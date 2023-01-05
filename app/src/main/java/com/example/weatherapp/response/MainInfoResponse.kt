package com.example.weatherapp.response

import com.google.gson.annotations.SerializedName

data class MainInfoResponse(
    @field:SerializedName("main")
    val main: MainDataResponse? = null,

)

data class MainDataResponse(
    @field:SerializedName("temp")
    val temp : Double? = null,

    @field:SerializedName("pressure")
    val pressure : Double? = null,

    @field:SerializedName("humidity")
    val humidity : Double? = null,

)


