package com.example.weatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert (weather : Weather)

    @Update
    fun update (weather: Weather)

    @Query("UPDATE weather SET city_name = :location WHERE id = :id")
    fun updateLocation (id:Int, location:String)

    @Query("UPDATE weather SET `temp` = :temp, pressure = :pressure, humidity = :humidity WHERE id = :id")
    fun updateMainWeather (id: Int, temp:Double?, pressure:Double?, humidity:Double?)

    @Query("UPDATE weather SET wind_speed = :wind WHERE id = :id")
    fun updateWind (id: Int, wind:Double?)

    @Query("SELECT * FROM weather WHERE id = :id")
    fun getWeatherInfo(id : Int) : LiveData<Weather>
}