package com.example.weatherapp.response

import com.google.gson.annotations.SerializedName

data class WindResponse(

    @field:SerializedName("wind")
    val wind : WindData? = null,

)

data class WindData(

    @field:SerializedName("speed")
    val speed : Double? = null,

)
