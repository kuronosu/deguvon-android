package dev.kuronosu.deguvon.datasource.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Episode(
    val number: Float,
    val eid: String,
    val url: String,
    val img: String
) : Parcelable