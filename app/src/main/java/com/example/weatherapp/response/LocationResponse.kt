package com.example.weatherapp.response

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @field:SerializedName("id")
    val id : Int? = null,

    @field:SerializedName("name")
    val name : String? = null,
)
