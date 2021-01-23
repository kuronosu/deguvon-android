package dev.kuronosu.deguvon.view.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dev.kuronosu.deguvon.R
import dev.kuronosu.deguvon.databinding.FragmentSearchBinding
import dev.kuronosu.deguvon.model.Anime
import dev.kuronosu.deguvon.utils.GridMarginItemDecorationLayout
import dev.kuronosu.deguvon.view.adapter.SearchAnimeAdapter
import dev.kuronosu.deguvon.view.adapter.SearchAnimeListener

class SearchFragment : Fragment(), SearchAnimeListener {

    private lateinit var viewModel: SearchViewModel

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchAdapter: SearchAnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.rvSearchResults.addItemDecoration(
            GridMarginItemDecorationLayout(
                resources.getDimension(R.dimen.home_rv_padding).toInt(),
                3
            )
        )
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchAdapter = SearchAnimeAdapter(this)
        binding.rvSearchResults.apply {
            adapter = searchAdapter
        }
        viewModel.animes.observe(viewLifecycleOwner, {
            searchAdapter.updateData(it)
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.downloadDirectory(requireContext())
    }

    override fun onAnimeClicked(episode: Anime, position: Int) {

    }
}