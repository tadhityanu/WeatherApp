package com.example.weatherapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.weatherapp.database.Weather
import com.example.weatherapp.database.WeatherDao
import com.example.weatherapp.database.WeatherDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class WeatherRepository(application: Application) {

    private val mWeatherDao : WeatherDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = WeatherDatabase.getDatabase(application)
        mWeatherDao = db.weatherDao()
    }

    fun getWeatherInfo(id : Int) : LiveData<Weather> = mWeatherDao.getWeatherInfo(id)

    fun insert(weather: Weather) {
        executorService.execute { mWeatherDao.insert(weather) }
    }

    fun update(weather: Weather){
        executorService.execute { mWeatherDao.update(weather) }
    }

    fun updatLocation(id:Int, location:String){
        executorService.execute { mWeatherDao.updateLocation(id, location) }
    }

    fun updateMainWeather(id: Int, temp:Double?, pressure:Double?, humidity:Double?){
        executorService.execute { mWeatherDao.updateMainWeather(id, temp, pressure, humidity) }
    }

    fun updateWind (id: Int, wind:Double?){
        executorService.execute { mWeatherDao.updateWind(id, wind) }
    }

}