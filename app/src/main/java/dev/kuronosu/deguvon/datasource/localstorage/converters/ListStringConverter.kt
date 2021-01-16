package dev.kuronosu.deguvon.datasource.localstorage.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListStringConverter {
    @TypeConverter
    fun toJsonString(data: List<String>): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun fromJsonString(jsonList: String): List<String> {
        return Gson().fromJson(jsonList, object : TypeToken<List<String>>() {}.type)
    }
}