package com.dmp.simplemorty.characters.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dmp.simplemorty.Constants

class CharacterSearchViewModel : ViewModel() {

    private var currentUserSearch: String = ""
    private var pagingSource: CharacterSearchPagingSource? = null
        get() {
            if (field == null || field?.invalid == true) {
                field = CharacterSearchPagingSource(currentUserSearch) { localException ->
                    Log.e("LOCAL", localException.toString())
                }
            }

            return field
        }

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        pagingSource!!
    }.flow.cachedIn(viewModelScope)

    fun submitQuery(userSearch: String) {
        currentUserSearch = userSearch
        pagingSource?.invalidate()
    }
}