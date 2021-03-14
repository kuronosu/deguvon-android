package dev.kuronosu.deguvon.datasource.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class LatestEpisode (
    val url: String,
    val image: String,
    val capi: String,
    val anime: Generic
) : Parcelable