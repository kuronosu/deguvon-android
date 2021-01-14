package dev.kuronosu.deguvon.view.ui.home

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kuronosu.deguvon.R
import dev.kuronosu.deguvon.databinding.FragmentHomeBinding
import dev.kuronosu.deguvon.model.LatestEpisode
import dev.kuronosu.deguvon.view.adapter.LatestEpisodesAdapter
import dev.kuronosu.deguvon.view.adapter.LatestEpisodesListener

class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                left = spaceHeight
            }
            top = spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}


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
            MarginItemDecoration(
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
        viewModel.latestEpisodes.observe(viewLifecycleOwner, { schedule ->
            latestEpisodesAdapter.updateData(schedule)
            binding.rvLatestEpisodes.visibility = View.VISIBLE
            binding.shimmerPlaceholderHome.visibility = View.INVISIBLE
        })
        /*viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it != null) binding..isRefreshing = it
        })*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refresh()
    }

    override fun onEpisodeClicked(episode: LatestEpisode, position: Int) {

    }
}