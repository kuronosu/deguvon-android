package dev.kuronosu.deguvon.datasource.network.model

import com.google.gson.annotations.SerializedName

data class RelationNetworkModel(
    val name: String,
    val url: String,
    @SerializedName("rel") val relation: String
)