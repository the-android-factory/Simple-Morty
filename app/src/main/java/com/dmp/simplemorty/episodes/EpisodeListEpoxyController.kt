package com.dmp.simplemorty.episodes

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.dmp.simplemorty.R
import com.dmp.simplemorty.databinding.ModelEpisodeListItemBinding
import com.dmp.simplemorty.domain.models.Episode
import com.dmp.simplemorty.epoxy.ViewBindingKotlinModel
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class EpisodeListEpoxyController : PagingDataEpoxyController<Episode>() {

    override fun buildItemModel(currentPosition: Int, item: Episode?): EpoxyModel<*> {
        return EpisodeListItemEpoxyModel(
            episode = item!!,
            onClick = { episodeId ->
                // todo
            }
        ).id("episode_${item.id}")
    }

    data class EpisodeListItemEpoxyModel(
        val episode: Episode,
        val onClick: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelEpisodeListItemBinding>(R.layout.model_episode_list_item) {

        override fun ModelEpisodeListItemBinding.bind() {
            episodeNameTextView.text = episode.name
            episodeAirDateTextView.text = episode.airDate
            episodeNumberTextView.text = episode.episode

            root.setOnClickListener { onClick(episode.id) }
        }
    }
}