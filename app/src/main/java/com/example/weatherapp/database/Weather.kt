package com.example.weatherapp.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "weather")
@Parcelize
data class Weather(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int = 0,

    @ColumnInfo(name = "city_name")
    var name : String? = null,

    @ColumnInfo(name = "temp")
    var temp : Double? = null,

    @ColumnInfo(name = "wind_speed")
    var speed : Double? = null,

    @ColumnInfo(name = "Pressure")
    var pressure : Double? = null,

    @ColumnInfo(name = "humidity")
    var humidity : Double? = null,

) : Parcelable
