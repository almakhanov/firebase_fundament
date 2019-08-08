package com.example.fundament.extensions

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fundament.R

fun ImageView.loadImage(url: String, fragment: Fragment) {
    Glide
        .with(fragment)
        .load(url)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(R.drawable.ic_placeholder)
        .into(this)
}
