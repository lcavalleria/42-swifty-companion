package com.lcavalle.switfy_companion.dataSources.api42

import android.widget.ImageView
import com.lcavalle.switfy_companion.R
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

object ImageLoader {
    fun loadImage(view: ImageView, imageUrl: String?) {
        if (imageUrl != null && imageUrl != "")
            Picasso
                .get()
                .load(imageUrl)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(view)
        else
            Picasso
                .get()
                .load(R.drawable.blank_image)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(view)
    }
}