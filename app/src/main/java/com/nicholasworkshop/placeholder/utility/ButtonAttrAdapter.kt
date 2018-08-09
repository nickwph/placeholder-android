package com.nicholasworkshop.placeholder.utility

import android.content.ActivityNotFoundException
import android.content.Intent
import android.databinding.BindingAdapter
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.Toast


@BindingAdapter("mapClickListener", "latitude", "longitude")
fun setMapClickListener(button: Button, mapClickListener: View.OnClickListener?, latitude: String?, longitude: String?) {
    button.setOnClickListener {
        try {
            val uri = "geo:$latitude,$longitude"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            button.context.startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            Toast.makeText(button.context, "Failed to open because there are no map apps detected.", Toast.LENGTH_LONG).show()
        }
    }
}