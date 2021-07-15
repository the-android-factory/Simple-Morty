package com.dmp.simplemorty.characters.search

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.dmp.simplemorty.R
import com.dmp.simplemorty.characters.CharacterListPagingEpoxyController
import com.dmp.simplemorty.databinding.ModelCharacterListItemBinding
import com.dmp.simplemorty.domain.models.Character
import com.dmp.simplemorty.epoxy.LoadingEpoxyModel
import com.dmp.simplemorty.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class CharacterSearchEpoxyController(
    private val onCharacterSelected: (Int) -> Unit
): PagingDataEpoxyController<Character>() {

    override fun buildItemModel(currentPosition: Int, item: Character?): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(
            characterId = item!!.id,
            imageUrl = item.image,
            name = item.name,
            onCharacterSelected = { characterId ->
                onCharacterSelected(characterId)
            }
        ).id(item.id)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        super.addModels(models)
    }

    data class CharacterGridItemEpoxyModel(
        val characterId: Int,
        val imageUrl: String,
        val name: String,
        val onCharacterSelected: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {

        override fun ModelCharacterListItemBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name

            root.setOnClickListener {
                onCharacterSelected(characterId)
            }
        }
    }
}