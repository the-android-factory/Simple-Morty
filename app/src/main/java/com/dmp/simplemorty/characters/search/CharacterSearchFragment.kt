package com.dmp.simplemorty.characters.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dmp.simplemorty.R
import com.dmp.simplemorty.databinding.FragmentCharacterSearchBinding
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterSearchFragment : Fragment(R.layout.fragment_character_search) {

    private var _binding: FragmentCharacterSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterSearchViewModel by viewModels()

    private var currentText = ""
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable {
        viewModel.submitQuery(currentText)
    }

    @ObsoleteCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterSearchBinding.bind(view)

        val epoxyController = CharacterSearchEpoxyController { characterId ->
            // todo navigate to details page with ID
        }
        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

        binding.searchEditText.doAfterTextChanged {
            currentText = it?.toString() ?: ""

            handler.removeCallbacks(searchRunnable)
            handler.postDelayed(searchRunnable, 500L)
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                epoxyController.localException = null
                epoxyController.submitData(pagingData)
            }
        }

        viewModel.localExceptionEventLiveData.observe(viewLifecycleOwner) { event ->
            event.getContent()?.let { localException ->
                epoxyController.localException = localException
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}