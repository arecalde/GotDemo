package ai.shield.app.shieldaichallenge.home

import ai.shield.app.shieldaichallenge.R
import ai.shield.app.shieldaichallenge.databinding.HomeFragmentBinding
import ai.shield.app.shieldaichallenge.databinding.ListItemBinding
import ai.shield.app.shieldaichallenge.helpers.ItemAdapter
import ai.shield.app.shieldaichallenge.model.Episode
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var episode: List<Episode>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = HomeFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.episodeList.observe(viewLifecycleOwner) {
            episode = it

            val adapter = ItemAdapter<Episode, ListItemBinding>(
                it,
                viewLifecycleOwner,
                R.layout.list_item
            ) { episode, listItemBinding ->
                listItemBinding.episode = episode
            }

            binding.episodeList.adapter = adapter
            adapter.selectedItem.observe(viewLifecycleOwner) {
                viewModel.updateEpisode(it)
            }

        }

        return binding.root
    }

}