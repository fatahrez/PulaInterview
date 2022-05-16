package com.fatahapps.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.fatahapps.data.local.model.OptionLocal
import com.fatahapps.data.local.util.JsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromOptionsJson(json: String): List<OptionLocal>? {
        return jsonParser.fromJson<ArrayList<OptionLocal>>(
            json,
            object : TypeToken<ArrayList<OptionLocal>>(){}.type
        )
    }

    @TypeConverter
    fun toOptionsJson(options: List<OptionLocal>): String {
        return jsonParser.toJson(
            options,
            object : TypeToken<ArrayList<OptionLocal>>(){}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fomStringsJson(json: String): List<String>? {
        return jsonParser.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>(){}.type
        )
    }

    @TypeConverter
    fun toStringsJson(strings: List<String>): String {
        return jsonParser.toJson(
            strings,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: "[]"
    }
}