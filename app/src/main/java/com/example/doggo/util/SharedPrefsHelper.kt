package com.example.doggo.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPrefsHelper {

    companion object {
        private const val PREF_TIME = "Pref Time"
        var sharedPrefs: SharedPreferences? = null

        @Volatile
        private var instance: SharedPrefsHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPrefsHelper = instance ?: synchronized(LOCK) {
            instance ?: buildHelper(context).also {
                instance = it
            }
        }

        private fun buildHelper(context: Context): SharedPrefsHelper {
            sharedPrefs = context.getSharedPreferences("databasePreferences", Context.MODE_PRIVATE)
            return SharedPrefsHelper()
        }
    }

    fun saveUpdateTime(time: Long) {
        sharedPrefs?.let {
            it.edit {
                putLong(PREF_TIME, time)
            }
        }
    }

    fun retrieveUpdateTime(): Long{
       return sharedPrefs?.getLong(PREF_TIME,0L)!!
    }
}