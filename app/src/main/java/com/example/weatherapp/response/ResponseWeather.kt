package com.example.weatherapp.response

import com.google.gson.annotations.SerializedName

data class ResponseWeather(
    @field:SerializedName("weather")
    val weather : List<WeatherResponse>? = null
)

data class WeatherResponse(
    @field:SerializedName("id")
    val id : Int? = null,

    @field:SerializedName("main")
    val main : String? = null,

    @field:SerializedName("description")
    val description : String? = null,

    @field:SerializedName("icon")
    val icon : String? = null,


)
