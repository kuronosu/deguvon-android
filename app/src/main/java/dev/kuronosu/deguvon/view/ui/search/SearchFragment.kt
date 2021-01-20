package dev.kuronosu.deguvon.view.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dev.kuronosu.deguvon.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.animes.observe(viewLifecycleOwner, {
            binding.searchResultCountText.text = it.size.toString()
        })
        binding.downloadDirectoryBtn.setOnClickListener {
            viewModel.downloadDirectory(requireContext())
        }
        return binding.root
    }
}