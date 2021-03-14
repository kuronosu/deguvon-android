package dev.kuronosu.deguvon.datasource.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
open class Generic(val id: Int, val name: String) : Parcelable