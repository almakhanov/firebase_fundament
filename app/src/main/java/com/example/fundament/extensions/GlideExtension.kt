package com.example.fundament.extensions

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fundament.R

fun ImageView.loadImage(url: String?, context: Context, placeholder: Int = R.drawable.ic_placeholder) {
    Glide
        .with(context)
        .load(url)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(placeholder)
        .into(this)
}
