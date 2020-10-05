package com.example.doggo.util

import androidx.room.TypeConverter
import com.example.doggo.model.more.Height
import com.example.doggo.model.more.Weight
import com.google.gson.Gson

class Converters {

    companion object {
        @JvmStatic
        @TypeConverter
        fun fromHeight(height: Height?): String? {
            return if (height == null) null else Gson().toJson(height)
        }

        @JvmStatic
        @TypeConverter
        fun toHeight(height: String?): Height? {
            return if (height == null) null else Gson().fromJson(height, Height::class.java)
        }


        @JvmStatic
        @TypeConverter
        fun fromWeight(weight: Weight?): String? {
            return if (weight == null) weight else Gson().toJson(weight)
        }

        @JvmStatic
        @TypeConverter
        fun toWeight(weight: String?): Weight? {
            return if (weight == null) null else Gson().fromJson(weight, Weight::class.java)
        }
    }
}