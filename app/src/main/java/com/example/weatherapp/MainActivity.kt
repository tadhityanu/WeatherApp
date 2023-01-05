package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.Api.apiConfig
import com.example.weatherapp.adapter.WeatherAdapter
import com.example.weatherapp.database.Weather
import com.example.weatherapp.database.WeatherDatabase
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.response.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : MainViewModel
    private lateinit var adapter : WeatherAdapter
    private lateinit var secondAdapter : WeatherAdapter
    companion object{
        private const val TAG = "MainActivity"
    }
    private var mWeather : Weather = Weather()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = obtainViewModel(this@MainActivity)

        setCurrentWeatherRecyclerView()
        getMainData()
        getLocationInfo()
        getWindInfo()

    }

    private fun setCurrentWeatherRecyclerView(){
        adapter = WeatherAdapter()

        binding.rvWeatherDesc.setHasFixedSize(true)
        binding.rvWeatherDesc.layoutManager = LinearLayoutManager(this)
        binding.rvWeatherDesc.adapter = adapter

        viewModel.setWeather()

        viewModel.getWeather().observe(this){
            if (it != null){
                adapter.setWeather(it)
                adapter.notifyDataSetChanged()
            }
        }

    }

    private fun getMainData(){
        val client = apiConfig.apiInstance.getMainInfo("Jakarta")
        client.enqueue(object : Callback<MainInfoResponse> {
            override fun onResponse(
                call: Call<MainInfoResponse>,
                response: Response<MainInfoResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body() != null){
                        setMainWeatherData(response.body()?.main)

                    }
                } else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MainInfoResponse>, t: Throwable) {
                viewModel.getWeatherInfo(1).observe(this@MainActivity, { _weather ->
                    if (_weather != null){
                        binding.txtTemp.text = _weather.temp.toString() + " °C"
                        binding.txtPressure.text = _weather.pressure.toString() + " hPa"
                        binding.txtHumidity.text = _weather.humidity.toString() + " %"
                    }
                })
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setMainWeatherData(weather : MainDataResponse?){

        binding.txtTemp.text = weather?.temp.toString() + " °C"
        binding.txtPressure.text = weather?.pressure.toString() + " hPa"
        binding.txtHumidity.text = weather?.humidity.toString() + " %"

        mWeather.let {
            it.temp = weather?.temp
            it.pressure = weather?.pressure
            it.humidity = weather?.humidity
        }


        if (mWeather.temp != null && mWeather.pressure != null && mWeather.humidity != null){
            viewModel.updateMainWeather(1, weather?.temp, weather?.pressure, weather?.humidity)
        }
        else {
            viewModel.insert(mWeather)
        }
    }

    private fun getLocationInfo(){

        val client = apiConfig.apiInstance.getLocationInfo("Jakarta")
        client.enqueue(object : Callback<LocationResponse> {
            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body() != null){

                        binding.txtCity.text = response.body()?.name

                        setLocationData(response.body())

                    }
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                viewModel.getWeatherInfo(1).observe(this@MainActivity, { _weather ->
                    if (_weather != null){
                        binding.txtCity.text = _weather.name
                    }
                })
            }
        })

    }

    private fun setLocationData(location:LocationResponse?){
        binding.txtCity.text = location?.name

        mWeather.let {
            it.name = location?.name
        }
            viewModel.insert(mWeather)
        if (mWeather.name != null){
            viewModel.updateLocation(1, location?.name!!)
        }
    }


    private fun getWindInfo(){
        val client = apiConfig.apiInstance.getWindInfo("Jakarta")
        client.enqueue(object : Callback<WindResponse> {
            override fun onResponse(call: Call<WindResponse>, response: Response<WindResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful){
                    if (responseBody != null){
                        setWindData(responseBody.wind)

                    }
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<WindResponse>, t: Throwable) {
                viewModel.getWeatherInfo(1).observe(this@MainActivity, { _weather ->
                    if (_weather != null){
                        binding.txtWindSpeed.text = _weather.speed.toString() + " m/sec"
                    }
                })
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setWindData(wind : WindData?){

        binding.txtWindSpeed.text = wind?.speed.toString() + " m/sec"

        mWeather.let {
            it.speed= wind?.speed
        }

        if (mWeather.speed != null){
            viewModel.updateWind(1, wind?.speed)
        } else {
            viewModel.insert(mWeather)
        }
    }

    private fun obtainViewModel(activity:AppCompatActivity):MainViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

}