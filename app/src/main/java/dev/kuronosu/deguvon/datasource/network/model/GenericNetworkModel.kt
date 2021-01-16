package dev.kuronosu.deguvon.datasource.network.model

import com.google.gson.annotations.SerializedName

data class GenericNetworkModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)