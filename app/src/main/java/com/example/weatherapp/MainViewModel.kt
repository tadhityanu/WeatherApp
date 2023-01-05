package com.example.weatherapp

import android.app.Application
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.Api.apiConfig
import com.example.weatherapp.database.Weather
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.response.ResponseWeather
import com.example.weatherapp.response.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(mApplication: Application) :ViewModel() {

    val listWeather =MutableLiveData<List<WeatherResponse>?>()
    private val mWeatherRepo : WeatherRepository = WeatherRepository(mApplication)

    fun setWeather(){
        val client = apiConfig.apiInstance.getWeather("Jakarta")
        client.enqueue(object : Callback<ResponseWeather>{
            override fun onResponse(
                call: Call<ResponseWeather>,
                response: Response<ResponseWeather>
            ) {
                if (response.isSuccessful){
                    listWeather.postValue(response.body()?.weather)
                } else{
                    listWeather.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseWeather>, t: Throwable) {
                listWeather.postValue(null)
            }
        })
    }

    fun getWeather():MutableLiveData<List<WeatherResponse>?>{
        return listWeather
    }

    fun insert(weather:Weather){
        mWeatherRepo.insert(weather)
    }

    fun getWeatherInfo(id:Int): LiveData<Weather> = mWeatherRepo.getWeatherInfo(id)

    fun updateLocation (id: Int, location:String){mWeatherRepo.updatLocation(id, location)}

    fun updateMainWeather (id: Int, temp:Double?, pressure:Double?, humidity:Double?){
        mWeatherRepo.updateMainWeather(id, temp, pressure, humidity)
    }

    fun updateWind (id: Int, wind:Double?){
        mWeatherRepo.updateWind(id, wind)
    }

}