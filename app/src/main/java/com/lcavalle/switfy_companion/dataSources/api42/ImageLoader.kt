package com.lcavalle.switfy_companion.dataSources.api42

import android.widget.ImageView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

object ImageLoader {
    fun loadImage(view: ImageView, imageUrl: String?) {
        Picasso
            .get()
            .load(imageUrl)
            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
            .into(view)
    }
}