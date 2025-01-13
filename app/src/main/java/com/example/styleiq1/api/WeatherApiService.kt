package com.example.styleiq1.api

import retrofit2.Call
import com.example.styleiq1.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {
    @GET("data/2.5/weather")
    fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "tr"
    ): Call<WeatherResponse>
}






