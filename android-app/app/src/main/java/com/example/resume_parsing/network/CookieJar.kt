package com.example.resume_parsing.network

import android.content.Context
import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import com.example.resume_parsing.utils.PreferencesHelper

class MyCookieJar(private val context: Context) : CookieJar {

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        for (cookie in cookies) {
            if (cookie.name == "token") {
                // Store the token in SharedPreferences
                PreferencesHelper.saveToken(context, cookie.value)
            }
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val token = PreferencesHelper.getToken(context)
        return if (token != null) {
            listOf(
                Cookie.Builder()
                    .name("token")
                    .value(token)
                    .domain(url.host)  // This is the correct way to access the domain
                    .build()
            )
        } else {
            emptyList()
        }
    }
}
