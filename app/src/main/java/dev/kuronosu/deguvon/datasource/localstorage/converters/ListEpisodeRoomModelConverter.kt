package dev.kuronosu.deguvon.datasource.localstorage.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.kuronosu.deguvon.datasource.localstorage.model.EpisodeRoomModel

class ListEpisodeRoomModelConverter {
    @TypeConverter
    fun toJsonString(data: List<EpisodeRoomModel>): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun fromJsonString(jsonList: String): List<EpisodeRoomModel> {
        return Gson().fromJson(jsonList, object : TypeToken<List<EpisodeRoomModel>>() {}.type)
    }
}
