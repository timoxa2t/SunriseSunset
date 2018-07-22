package com.example.alex.sunrisesunset.sunriseSunset

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SunriseSunsetApi {
    @GET("json")
    fun getCoords(@Query("lat") latitude: Double, @Query("lng") longitude: Double): Call<SunriseSunsetPOJO>

    companion object Factory{
        fun create(): SunriseSunsetApi {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.sunrise-sunset.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(SunriseSunsetApi::class.java)
        }
    }
}