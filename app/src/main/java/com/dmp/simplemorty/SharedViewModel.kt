package com.dmp.simplemorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmp.simplemorty.domain.models.Character
import com.dmp.simplemorty.network.SimpleMortyCache
import com.dmp.simplemorty.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.launch

class SharedViewModel: ViewModel() {

    private val repository = SharedRepository()

    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdLiveData: LiveData<Character?> = _characterByIdLiveData

    fun fetchCharacter(characterId: Int) {

        // Check the cache for our character
        val cachedCharacter = SimpleMortyCache.characterMap[characterId]
        if (cachedCharacter != null) {
            _characterByIdLiveData.postValue(cachedCharacter)
            return
        }

        // Otherwise, we need to make the network call for the character
        viewModelScope.launch {
            val response = repository.getCharacterById(characterId)

            _characterByIdLiveData.postValue(response)

            // Update cache if non-null char received
            response?.let {
                SimpleMortyCache.characterMap[characterId] = it
            }
        }
    }
}