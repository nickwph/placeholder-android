package com.nicholasworkshop.placeholder.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View.MeasureSpec.getSize
import android.widget.ImageView


class SquareImageView : ImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = getSize(widthMeasureSpec)
        setMeasuredDimension(width, width)
    }
}