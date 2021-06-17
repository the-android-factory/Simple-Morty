package com.dmp.simplemorty.episodes

import com.dmp.simplemorty.network.NetworkLayer
import com.dmp.simplemorty.network.response.GetEpisodesPageResponse

class EpisodeRepository {

    suspend fun fetchEpisodesPage(pageIndex: Int): GetEpisodesPageResponse? {
        val pageRequest = NetworkLayer.apiClient.getEpisodesPage(pageIndex)

        if (!pageRequest.isSuccessful) {
            return null
        }

        return pageRequest.body
    }
}