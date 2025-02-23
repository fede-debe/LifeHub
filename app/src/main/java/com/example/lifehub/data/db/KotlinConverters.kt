package com.example.lifehub.data.db

import androidx.room.TypeConverter
import com.example.lifehub.data.model.TodoItem
import com.example.lifehub.util.ConfiguredGsonBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class KotlinConverters {
    private val gson = ConfiguredGsonBuilder().builder.create()

    private inline fun <reified T> Gson.fromJson(json: String) =
        this.fromJson<T>(json, object : TypeToken<T>() {}.type)!!

    @TypeConverter
    fun todoItemListToJson(list: List<TodoItem>?): String? {
        return if (list == null) null else gson.toJson(list)
    }

    @TypeConverter
    fun todoItemListFromJson(json: String?): List<TodoItem> {
        return if (json == null) listOf() else gson.fromJson(json)
    }
}