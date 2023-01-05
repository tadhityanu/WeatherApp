package com.example.weatherapp.Api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object apiConfig {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val loggingIntercepetor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val client =OkHttpClient.Builder()
        .addInterceptor(loggingIntercepetor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val apiInstance = retrofit.create(apiService::class.java)
}