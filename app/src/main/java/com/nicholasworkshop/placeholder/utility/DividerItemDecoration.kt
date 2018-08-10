package com.nicholasworkshop.placeholder.utility

import android.content.Context
import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import com.nicholasworkshop.placeholder.R


class SimpleDividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val drawable = context.getDrawable(R.drawable.divider_line)


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + drawable.intrinsicHeight
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(c)
        }
    }
}
