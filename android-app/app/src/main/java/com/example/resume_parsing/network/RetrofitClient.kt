package com.example.resume_parsing.network

import android.util.Log
import com.example.resume_parsing.App
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://172.20.140.135:8000/"  // Replace with your backend URL

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d("RetrofitHTTP", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY  // This will log request and response bodies
    }

    private val client = OkHttpClient.Builder()
        .cookieJar(MyCookieJar(App.context)) // Use the CookieJar to handle cookies
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
