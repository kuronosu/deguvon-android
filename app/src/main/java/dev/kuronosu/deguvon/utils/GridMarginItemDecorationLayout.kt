package dev.kuronosu.deguvon.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class GridMarginItemDecorationLayout(private val space: Int, private val spanCount: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount
            left = space - column * space / spanCount
            right = (column + 1) * space / spanCount
            if (position < spanCount) {
                outRect.top = space
            }
            bottom = space
        }
    }
}