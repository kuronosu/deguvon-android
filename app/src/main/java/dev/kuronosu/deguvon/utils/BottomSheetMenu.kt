package dev.kuronosu.deguvon.utils


import android.os.Bundle
import android.view.*
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.iterator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.kuronosu.deguvon.databinding.FragmentBottomSheetMenuDialogBinding


const val KEY_BOTTOM_SHEET_MENU_XML_MENU_RESOURCE = "XML_MENU_RESOURCE"
const val KEY_BOTTOM_SHEET_MENU_TITLE = "TITLE"

open class BottomSheetMenu : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetMenuDialogBinding? = null
    private val binding get() = _binding!!

    lateinit var menu: Menu
    lateinit var handler: MenuItem.OnMenuItemClickListener
    private lateinit var adapter: BottomSheetMenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetMenuDialogBinding.inflate(inflater, container, false)
//        handler = { _, _ -> } // TODO("find a way to pass a callback when navigate")
        adapter = BottomSheetMenuAdapter(handler)
        binding.rvMenu.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvMenu.adapter = adapter
        binding.rvMenu.addItemDecoration(
            DividerItemDecoration(binding.rvMenu.context, DividerItemDecoration.HORIZONTAL)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = requireArguments().getString(KEY_BOTTOM_SHEET_MENU_TITLE)
        if (binding.title.text.isNullOrBlank()) {
            binding.title.visibility = View.GONE
        }
        loadMenu(requireArguments().getInt(KEY_BOTTOM_SHEET_MENU_XML_MENU_RESOURCE))
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
            listener: MenuItem.OnMenuItemClickListener
        ) = BottomSheetMenu().apply {
            arguments = Bundle().apply {
                putInt(KEY_BOTTOM_SHEET_MENU_XML_MENU_RESOURCE, xmlRes)
                putString(KEY_BOTTOM_SHEET_MENU_TITLE, title)
            }
            handler = listener
        }

        fun newInstance(@MenuRes xmlRes: Int, listener: MenuItem.OnMenuItemClickListener) =
            newInstance(xmlRes, "", listener)
    }
}
