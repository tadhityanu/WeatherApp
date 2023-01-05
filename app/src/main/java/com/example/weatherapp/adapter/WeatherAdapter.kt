package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.databinding.ItemWeatherBinding
import com.example.weatherapp.response.WeatherResponse

class WeatherAdapter:RecyclerView.Adapter<WeatherAdapter.ListWeatherAdapter>() {

    private var listWeather :List<WeatherResponse>? = null

    fun setWeather(weather : List<WeatherResponse>?){
        this.listWeather = weather
        notifyDataSetChanged()
    }

    inner class ListWeatherAdapter(val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(weatherResponse : WeatherResponse){
            binding.apply {
                txtWeatherDesc.text = weatherResponse.description
                Glide.with(itemView)
                    .load("https://openweathermap.org/img/wn/${weatherResponse.icon}.png")
                    .apply(RequestOptions().override(250, 250))
                    .into(imgWeather)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListWeatherAdapter {
        val view = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListWeatherAdapter(view)
    }

    override fun onBindViewHolder(holder: ListWeatherAdapter, position: Int) {
        listWeather?.get(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return if (listWeather == null) 0
//        else listWeather?.size!!
        else 1
    }

}