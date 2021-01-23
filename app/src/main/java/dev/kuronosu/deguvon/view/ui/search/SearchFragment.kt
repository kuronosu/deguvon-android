package dev.kuronosu.deguvon.view.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.setUpContext(requireContext())
        val columnSpace = resources.getDimension(R.dimen.home_rv_padding).toInt()
        val columns = resources.getInteger(R.integer.search_columns)
        binding.rvSearchResults.layoutManager = GridLayoutManager(binding.root.context, columns)
        binding.rvSearchResults.addItemDecoration(
            GridMarginItemDecorationLayout(columnSpace, columns)
        )
        searchAdapter = SearchAnimeAdapter(this)
        binding.rvSearchResults.apply {
            adapter = searchAdapter
        }
        viewModel.animes.observe(viewLifecycleOwner, {
            searchAdapter.updateData(it)
        })
        initScrollListener()
        return binding.root
    }

    private fun initScrollListener() {
        binding.rvSearchResults.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int, dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvSearchResults.layoutManager as GridLayoutManager
                if (layoutManager.childCount + layoutManager.findLastVisibleItemPosition() >= layoutManager.itemCount) {
                    viewModel.loadAnimes()
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadAnimes()
    }

    override fun onAnimeClicked(episode: Anime, position: Int) {

    }
}