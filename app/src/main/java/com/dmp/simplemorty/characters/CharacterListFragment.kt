package com.dmp.simplemorty.characters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dmp.simplemorty.BaseFragment
import com.dmp.simplemorty.R
import com.dmp.simplemorty.databinding.FragmentCharacterListBinding

class CharacterListFragment : BaseFragment(R.layout.fragment_character_list) {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharactersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterListBinding.bind(view)

        val epoxyController = CharacterListPagingEpoxyController(::onCharacterSelected)

        viewModel.charactersPagedListLiveData.observe(viewLifecycleOwner) { pagedList ->
            epoxyController.submitList(pagedList)
        }

        binding.epoxyRecyclerView.setController(epoxyController)
    }

    private fun onCharacterSelected(characterId: Int) {
        val directions =
            CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                characterId = characterId
            )
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}