package dev.kuronosu.deguvon.ui.search.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kuronosu.deguvon.R
import dev.kuronosu.deguvon.databinding.FragmentSearchBinding
import dev.kuronosu.deguvon.datasource.model.Anime
import dev.kuronosu.deguvon.ui.search.SearchViewModel
import dev.kuronosu.deguvon.utils.GridMarginItemDecorationLayout


class SearchFragment : Fragment(), SearchAnimeListener {
    private val viewModel: SearchViewModel by navGraphViewModels(R.id.mobile_navigation)

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchAdapter: SearchAnimeAdapter

    //Search
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchAnimes(newText!!)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchAnimes(query!!)
                return true
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        //viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
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

    override fun onStart() {
        super.onStart()
        viewModel.initialLoad()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView?.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        searchView?.setOnQueryTextListener(queryTextListener)
        return when (item.itemId) {
            R.id.search -> false
            else -> super.onOptionsItemSelected(item)
        }
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
                    viewModel.loadMore()
                }
            }
        })
    }

    override fun onAnimeClicked(anime: Anime, position: Int) {

    }
}