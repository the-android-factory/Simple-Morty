package com.dmp.simplemorty.characters

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.dmp.simplemorty.R
import com.dmp.simplemorty.databinding.ModelCharacterListItemBinding
import com.dmp.simplemorty.databinding.ModelCharacterListTitleBinding
import com.dmp.simplemorty.epoxy.LoadingEpoxyModel
import com.dmp.simplemorty.epoxy.ViewBindingKotlinModel
import com.dmp.simplemorty.network.response.GetCharacterByIdResponse
import com.squareup.picasso.Picasso
import java.util.*

class CharacterListPagingEpoxyController: PagedListEpoxyController<GetCharacterByIdResponse>() {

    override fun buildItemModel(
        currentPosition: Int,
        item: GetCharacterByIdResponse?
    ): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(item!!.image, item.name).id(item.id)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {

        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        CharacterGridTitleEpoxyModel("Main Family")
            .id("main_family_header")
            .addTo(this)

        super.addModels(models.subList(0, 5))

        (models.subList(5, models.size) as List<CharacterGridItemEpoxyModel>).groupBy {
            it.name[0].toUpperCase()
        }.forEach { mapEntry ->
            val character = mapEntry.key.toString().toUpperCase(Locale.US)
            CharacterGridTitleEpoxyModel(title = character)
                .id(character)
                .addTo(this)

            super.addModels(mapEntry.value)
        }
    }

    // region Data Classes
    data class CharacterGridItemEpoxyModel(
        val imageUrl: String,
        val name: String
    ): ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {

        override fun ModelCharacterListItemBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name
        }
    }

    data class CharacterGridTitleEpoxyModel(
        val title: String
    ): ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title) {

        override fun ModelCharacterListTitleBinding.bind() {
            textView.text = title
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
    // endregion Data Classes
}