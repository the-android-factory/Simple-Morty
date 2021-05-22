package com.dmp.simplemorty.domain.mappers

import com.dmp.simplemorty.domain.models.Episode
import com.dmp.simplemorty.network.response.GetEpisodeByIdResponse

object EpisodeMapper {

    fun buildFrom(networkEpisode: GetEpisodeByIdResponse): Episode {
        return Episode(
            id = networkEpisode.id,
            name = networkEpisode.name,
            airDate = networkEpisode.air_date,
            episode = networkEpisode.episode
        )
    }
}