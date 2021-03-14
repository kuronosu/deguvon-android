package dev.kuronosu.deguvon.utils

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.kuronosu.deguvon.R


class BottomSheetMenuAdapter(private val listener: MenuItem.OnMenuItemClickListener) :
    RecyclerView.Adapter<BottomSheetMenuAdapter.ViewHolder>() {

    private val items = ArrayList<MenuItem>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
        val tvText: TextView = itemView.findViewById(R.id.tv_menu_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_menu_bottom_sheet, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvText.text = item.title
        holder.ivIcon.setImageDrawable(item.icon)
        holder.itemView.setOnClickListener {
            listener.onMenuItemClick(item)
        }
    }

    override fun getItemCount() = items.size

    fun updateData(data: List<MenuItem>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }
}