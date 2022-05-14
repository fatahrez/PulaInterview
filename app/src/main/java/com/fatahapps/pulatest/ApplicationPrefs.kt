package com.fatahapps.pulatest

import android.content.SharedPreferences

class ApplicationPrefs {
    private val preferences: SharedPreferences = PulaApp.self().getSharedPreferences(
        Constants.APPLICATION_PREFS, 0
    )

    fun isNotFirstTime(): Boolean {
        return preferences.getBoolean(Constants.IS_FIRST_TIME, false)
    }

    fun setNotFirstTime(b: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(Constants.IS_FIRST_TIME, b).apply()
    }
}