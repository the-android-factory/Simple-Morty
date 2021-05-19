package com.dmp.simplemorty.domain.mappers

import com.dmp.simplemorty.domain.models.Character
import com.dmp.simplemorty.network.response.GetCharacterByIdResponse

object CharacterMapper {

    fun buildFrom(response: GetCharacterByIdResponse): Character {
        return Character(
            episodeList = emptyList(), // todo
            gender = response.gender,
            id = response.id,
            image = response.image,
            location = Character.Location(
                name = response.location.name,
                url = response.location.url
            ),
            name = response.name,
            origin = Character.Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            species = response.species,
            status = response.status
        )
    }
}