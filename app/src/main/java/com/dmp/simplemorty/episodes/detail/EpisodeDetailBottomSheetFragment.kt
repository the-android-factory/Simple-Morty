package com.dmp.simplemorty.episodes.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dmp.simplemorty.databinding.FragmentEpisodeDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EpisodeDetailBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEpisodeDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EpisodeDetailViewModel by viewModels()
    private val safeArgs: EpisodeDetailBottomSheetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpisodeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.episodeLiveData.observe(viewLifecycleOwner) { episode ->
            if (episode == null) {
                // todo handle error
                return@observe
            }

            binding.episodeNameTextView.text = episode.name
            binding.episodeAirDateTextView.text = episode.airDate
            binding.episodeNumberTextView.text = episode.getFormattedSeason()

            binding.epoxyRecyclerView.setControllerAndBuildModels(
                EpisodeDetailEpoxyController(episode.characters)
            )
        }

        viewModel.fetchEpisode(safeArgs.episodeId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}