package com.dmp.simplemorty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView

class CharacterDetailFragment : Fragment() {

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    private val epoxyController = CharacterDetailsEpoxyController()

    private val safeArgs: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.characterByIdLiveData.observe(viewLifecycleOwner) { character ->

            epoxyController.character = character

            if (character == null) {
                Toast.makeText(
                    requireActivity(),
                    "Unsuccessful network call!",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigateUp()
                return@observe
            }
        }

        viewModel.fetchCharacter(characterId = safeArgs.characterId)

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }
}