package com.dmp.simplemorty.network

import com.dmp.simplemorty.network.response.GetCharacterByIdResponse
import com.dmp.simplemorty.network.response.GetCharactersPageResponse
import com.dmp.simplemorty.network.response.GetEpisodeByIdResponse
import retrofit2.Response

class ApiClient(
    private val rickAndMortyService: RickAndMortyService
) {

    suspend fun getCharacterById(characterId: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall { rickAndMortyService.getCharacterById(characterId) }
    }

    suspend fun getCharactersPage(pageIndex: Int): SimpleResponse<GetCharactersPageResponse> {
        return safeApiCall { rickAndMortyService.getCharactersPage(pageIndex) }
    }

    suspend fun getEpisodeById(episodeId: Int): SimpleResponse<GetEpisodeByIdResponse> {
        return safeApiCall { rickAndMortyService.getEpisodeById(episodeId) }
    }

    suspend fun getEpisodeRange(episodeRange: String): SimpleResponse<List<GetEpisodeByIdResponse>> {
        return safeApiCall { rickAndMortyService.getEpisodeRange(episodeRange) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}