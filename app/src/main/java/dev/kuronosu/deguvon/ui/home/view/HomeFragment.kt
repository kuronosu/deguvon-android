package dev.kuronosu.deguvon.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dev.kuronosu.deguvon.R
import dev.kuronosu.deguvon.databinding.FragmentHomeBinding
import dev.kuronosu.deguvon.datasource.model.LatestEpisode
import dev.kuronosu.deguvon.ui.home.HomeViewModel
import dev.kuronosu.deguvon.utils.BottomSheetMenu
import dev.kuronosu.deguvon.utils.HorizontalMarginItemDecorationLayout


class HomeFragment : Fragment(), LatestEpisodesListener {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var latestEpisodesAdapter: LatestEpisodesAdapter
    private val viewModel: HomeViewModel by navGraphViewModels(R.id.mobile_navigation)
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
        viewModel.setUpContext(requireContext())
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

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
    }

    override fun onEpisodeClicked(episode: LatestEpisode, position: Int) {
    }

    override fun onEpisodeClickedLong(episode: LatestEpisode, position: Int) {
        BottomSheetMenu.newInstance(
            R.menu.latest_episode_clicked_menu, episode.anime.name
        ) { it, c ->
            val toast = Toast.makeText(c, "Próximamente", Toast.LENGTH_SHORT)
            when (it.itemId) {
                R.id.play -> toast.show()
                R.id.download -> toast.show()
                R.id.anime_details -> toast.show()
                R.id.mark_as_seen -> toast.show()
            }
        }.show(parentFragmentManager, "bottom_sheet_menu")
    }
}