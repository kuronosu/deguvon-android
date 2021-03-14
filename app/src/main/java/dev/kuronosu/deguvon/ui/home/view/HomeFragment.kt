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
    private val viewmodel: HomeViewModel by navGraphViewModels(R.id.mobile_navigation)
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
        //viewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)
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

    override fun onEpisodeClickedLong(episode: LatestEpisode, position: Int) {
        BottomSheetMenu.newInstance(R.menu.latest_episode_clicked_menu) {
            when (it.itemId) {
                R.id.play -> Toast.makeText(context, "Próximamente (Play)", Toast.LENGTH_SHORT)
                    .show()
                R.id.download -> Toast.makeText(
                    context,
                    "Próximamente (Download)",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.anime_details -> Toast.makeText(
                    context,
                    "Próximamente (Anime details)",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.mark_as_seen -> Toast.makeText(
                    context,
                    "Próximamente (Mark as seen)",
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }.show(parentFragmentManager, "dialog")

//        val bundle = bundleOf(
//            KEY_BOTTOM_SHEET_MENU_TITLE to "Menu",
//            KEY_BOTTOM_SHEET_MENU_XML_MENU_RESOURCE to R.menu.latest_episode_clicked_menu
//        )
//        findNavController().navigate(R.id.navigation_bottom_sheet_menu, bundle)
    }
}