
package com.example.resume_parsing.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.resume_parsing.network.UserResponse
import com.google.gson.Gson

object PreferencesHelper {
    private const val PREF_NAME = "user_prefs"
    private const val TOKEN_KEY = "token"
    private const val USER_DATA_KEY = "user_data"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(context: Context, token: String) {
        getPreferences(context).edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(context: Context): String? {
        return getPreferences(context).getString(TOKEN_KEY, null)
    }

    fun saveUserData(context: Context, user: UserResponse) {
        val json = Gson().toJson(user)
        getPreferences(context).edit().putString(USER_DATA_KEY, json).apply()
    }

    fun getUserData(context: Context): UserResponse? {
        val json = getPreferences(context).getString(USER_DATA_KEY, null)
        return json?.let { Gson().fromJson(it, UserResponse::class.java) }
    }

    fun clearPreferences(context: Context) {
        getPreferences(context).edit().clear().apply()
    }
}
