package dev.kuronosu.deguvon.datasource.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Relation(
    val name: String,
    val url: String,
    val relation: String
) : Parcelable
