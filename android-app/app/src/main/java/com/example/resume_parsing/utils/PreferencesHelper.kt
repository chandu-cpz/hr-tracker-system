package com.example.resume_parsing.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesHelper {
    private const val PREF_NAME = "user_prefs"
    private const val TOKEN_KEY = "token"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(context: Context, token: String) {
        val prefs = getPreferences(context)
        with(prefs.edit()) {
            putString(TOKEN_KEY, token)
            apply()  // Use apply() for asynchronous saving
        }
    }

    fun getToken(context: Context): String? {
        val prefs = getPreferences(context)
        return prefs.getString(TOKEN_KEY, null)  // Returns null if no token is saved
    }
    fun clearToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("token")  // Assuming "token" is the key where the token is stored
        editor.apply()
    }
}
