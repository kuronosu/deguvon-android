package dev.kuronosu.deguvon.datasource.network.model

import com.google.gson.annotations.SerializedName

data class DirectoryNetworkModel(
    @SerializedName("states") val states: List<GenericNetworkModel>,
    @SerializedName("types") val types: List<GenericNetworkModel>,
    @SerializedName("genres") val genres: List<GenericNetworkModel>,
    @SerializedName("animes") val animes: HashMap<Int, AnimeNetworkModel>
)