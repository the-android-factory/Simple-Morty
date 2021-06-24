package com.dmp.simplemorty.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.dmp.simplemorty.Constants
import com.dmp.simplemorty.domain.models.Episode
import kotlinx.coroutines.flow.map

class EpisodesViewModel : ViewModel() {

    private val repository = EpisodeRepository()
    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        EpisodePagingSource(repository)
    }.flow.cachedIn(viewModelScope).map {
        it.insertSeparators { model: EpisodesUiModel?, model2: EpisodesUiModel? ->

            // Initial separator for the first season header (before the whole list)
            if (model == null) {
                return@insertSeparators EpisodesUiModel.Header("Season 1")
            }

            // No footer
            if (model2 == null) {
                return@insertSeparators null
            }

            // Make sure we only care about the items (episodes)
            if (model is EpisodesUiModel.Header || model2 is EpisodesUiModel.Header) {
                return@insertSeparators null
            }

            // Little logic to determine if a separator is necessary
            val episode1 = (model as EpisodesUiModel.Item).episode
            val episode2 = (model2 as EpisodesUiModel.Item).episode
            return@insertSeparators if (episode2.seasonNumber != episode1.seasonNumber) {
                EpisodesUiModel.Header("Season ${episode2.seasonNumber}")
            } else {
                null
            }
        }
    }
}