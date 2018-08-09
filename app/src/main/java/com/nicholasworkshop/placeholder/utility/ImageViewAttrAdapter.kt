package com.nicholasworkshop.placeholder.utility

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import timber.log.Timber


@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Timber.d("Loading image url: $url")
    Glide.with(imageView.context).load(url).into(imageView)
}