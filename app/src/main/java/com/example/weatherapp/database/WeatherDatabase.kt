package com.example.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Weather::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao() : WeatherDao

    companion object{
        @Volatile
        private var INSTANCE : WeatherDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context) : WeatherDatabase{
            if (INSTANCE == null){
                synchronized(WeatherDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    WeatherDatabase::class.java, "weather_database").build()
                }
            }
            return INSTANCE as WeatherDatabase
        }
    }
}