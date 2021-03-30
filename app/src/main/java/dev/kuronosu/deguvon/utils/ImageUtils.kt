package dev.kuronosu.deguvon.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable


fun ImageView.load(url: String) {
    Glide
        .with(this.context)
        .load(url)
        .transition(withCrossFade())
        .into(this)
}

fun ImageView.load(url: String, placeholder: Drawable) {
    Glide
        .with(this.context)
        .load(url)
        .placeholder(placeholder)
        .transition(withCrossFade())
        .into(this)
}

fun ImageView.loadWithShimmerPlaceholder(url: String) {
    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(
            Shimmer.ColorHighlightBuilder()
                .setHighlightColor(-1)
                .setBaseAlpha(0.9f)
                .setBaseColor(-1)
                .build()
        )
    }
    load(url, shimmerDrawable)
}