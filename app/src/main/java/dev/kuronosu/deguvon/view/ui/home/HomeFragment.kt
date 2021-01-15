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
import dev.kuronosu.deguvon.utils.MarginItemDecorationHorizontalLayout
import dev.kuronosu.deguvon.view.adapter.LatestEpisodesAdapter
import dev.kuronosu.deguvon.view.adapter.LatestEpisodesListener


class HomeFragment : Fragment(), LatestEpisodesListener {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var latestEpisodesAdapter: LatestEpisodesAdapter
    private lateinit var viewModel: HomeViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.rvLatestEpisodes.addItemDecoration(
            MarginItemDecorationHorizontalLayout(
                resources.getDimension(R.dimen.home_rv_padding).toInt()
            )
        )
        val view = binding.root
        viewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        latestEpisodesAdapter = LatestEpisodesAdapter(this)
        binding.rvLatestEpisodes.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = latestEpisodesAdapter
        }
        observeViewModel()
        return view
    }

    private fun observeViewModel() {
        viewModel.latestEpisodes.observe(viewLifecycleOwner, { latestEpisodes ->
            latestEpisodesAdapter.updateData(latestEpisodes)
            binding.rvLatestEpisodes.visibility = View.VISIBLE
            binding.shimmerPlaceholderHomeLatestEpisodes.visibility = View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refresh(view.context)
    }

    override fun onEpisodeClicked(episode: LatestEpisode, position: Int) {

    }
}