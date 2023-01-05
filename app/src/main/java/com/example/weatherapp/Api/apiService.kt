package com.example.weatherapp.Api

import com.example.weatherapp.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface apiService {

    @GET("weather?&appid=f8c37b3b2070155166d7d8c157b5c74f&units=metric")
    fun getWeather(
        @Query("q") query: String
    ): Call<ResponseWeather>

    @GET("weather?&appid=f8c37b3b2070155166d7d8c157b5c74f&units=metric")
    fun getMainInfo(
        @Query("q") query: String
    ): Call<MainInfoResponse>

    @GET("weather?&appid=f8c37b3b2070155166d7d8c157b5c74f&units=metric")
    fun getLocationInfo(
        @Query("q") query: String
    ): Call<LocationResponse>

    @GET("weather?&appid=f8c37b3b2070155166d7d8c157b5c74f&units=metric")
    fun getWindInfo(
        @Query("q") query: String
    ): Call<WindResponse>
}