package dev.kuronosu.deguvon.utils


import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.iterator
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.kuronosu.deguvon.R
import dev.kuronosu.deguvon.databinding.FragmentBottomSheetMenuDialogBinding


const val KEY_BOTTOM_SHEET_MENU_XML_MENU_RESOURCE = "XML_MENU_RESOURCE"
const val KEY_BOTTOM_SHEET_MENU_TITLE = "TITLE"

typealias ClickHandler = (MenuItem, Context) -> Unit

open class BottomSheetMenu : BottomSheetDialogFragment() {
    private val viewModel: BottomSheetMenuViewModel by navGraphViewModels(R.id.mobile_navigation)
    private var _binding: FragmentBottomSheetMenuDialogBinding? = null
    private val binding get() = _binding!!

    lateinit var menu: Menu
    private lateinit var adapter: BottomSheetMenuAdapter

    // Use to set the handle in the navigation moment, not call directly, use the handle in the view model
    private var _handler: ClickHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetMenuDialogBinding.inflate(inflater, container, false)
        if (_handler != null) {
            viewModel.setHandler(_handler!!)
        }
        return binding.root
    }

    private fun setUpAdapter(h: ClickHandler) {
        adapter = BottomSheetMenuAdapter(h)
        binding.rvMenu.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = requireArguments().getString(KEY_BOTTOM_SHEET_MENU_TITLE)
        if (binding.title.text.isNullOrBlank()) {
            binding.title.visibility = View.GONE
        }
        viewModel.handler.observe(viewLifecycleOwner) {
            setUpAdapter(it)
            binding.rvMenu.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rvMenu.addItemDecoration(
                DividerItemDecoration(binding.rvMenu.context, DividerItemDecoration.HORIZONTAL)
            )
            loadMenu(requireArguments().getInt(KEY_BOTTOM_SHEET_MENU_XML_MENU_RESOURCE))
        }
    }

    private fun loadMenu(@MenuRes xmlRes: Int) {
        val p = PopupMenu(requireContext(), requireView())
        menu = p.menu
        MenuInflater(requireContext()).inflate(xmlRes, menu)
        val list = ArrayList<MenuItem>()
        menu.iterator().forEach { list.add(it) }
        adapter.updateData(list)
    }

    companion object {
        fun newInstance(
            @MenuRes xmlRes: Int,
            title: String,
            handler: ClickHandler
        ) = BottomSheetMenu().apply {
            arguments = Bundle().apply {
                putInt(KEY_BOTTOM_SHEET_MENU_XML_MENU_RESOURCE, xmlRes)
                putString(KEY_BOTTOM_SHEET_MENU_TITLE, title)
            }
            this._handler = handler
        }

        fun newInstance(@MenuRes xmlRes: Int, handler: ClickHandler) =
            newInstance(xmlRes, "", handler)
    }
}
