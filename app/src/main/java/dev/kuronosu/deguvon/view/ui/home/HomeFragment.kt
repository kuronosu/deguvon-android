package dev.kuronosu.deguvon.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.kuronosu.deguvon.R
import dev.kuronosu.deguvon.databinding.FragmentHomeBinding
import dev.kuronosu.deguvon.model.LatestEpisode
import dev.kuronosu.deguvon.utils.HorizontalMarginItemDecorationLayout
import dev.kuronosu.deguvon.view.adapter.LatestEpisodesAdapter
import dev.kuronosu.deguvon.view.adapter.LatestEpisodesListener


class HomeFragment : Fragment(), LatestEpisodesListener {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var latestEpisodesAdapter: LatestEpisodesAdapter
    private lateinit var viewmodel: HomeViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.rvLatestEpisodes.addItemDecoration(
            HorizontalMarginItemDecorationLayout(
                resources.getDimension(R.dimen.home_rv_padding).toInt()
            )
        )
        viewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewmodel.setUpContext(requireContext())
        latestEpisodesAdapter = LatestEpisodesAdapter(this)
        binding.rvLatestEpisodes.apply {
            layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = latestEpisodesAdapter
        }
        observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        viewmodel.latestEpisodes.observe(viewLifecycleOwner, { latestEpisodes ->
            latestEpisodesAdapter.updateData(latestEpisodes)
            binding.rvLatestEpisodes.visibility = View.VISIBLE
            binding.shimmerPlaceholderHomeLatestEpisodes.visibility = View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        viewmodel.refresh()
    }

    override fun onEpisodeClicked(episode: LatestEpisode, position: Int) {

    }
}