package com.dmp.simplemorty.episodes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.dmp.simplemorty.NavGraphDirections
import com.dmp.simplemorty.R
import com.dmp.simplemorty.databinding.FragmentEpisodeListBinding
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeListFragment : Fragment(R.layout.fragment_episode_list) {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EpisodesViewModel by viewModels()

    @ObsoleteCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodeListBinding.bind(view)

        val epoxyController = EpisodeListEpoxyController { episodeClickedId ->
            val navDirections = NavGraphDirections.actionGlobalToEpisodeDetailBottomSheetFragment(
                episodeId = episodeClickedId
            )
            findNavController().navigate(navDirections)
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData: PagingData<EpisodesUiModel> ->
                epoxyController.submitData(pagingData)
            }
        }

        binding.epoxyRecyclerView.setController(epoxyController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}