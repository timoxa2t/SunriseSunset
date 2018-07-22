package com.example.alex.sunrisesunset

import com.example.alex.sunrisesunset.Autocomplete.AutocompletePOJO
import com.example.alex.sunrisesunset.CityDetails.DetailsPOJO
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapsAPI {

    companion object {
        const val API_KEY = "AIzaSyBp77XzS1mRtG3tCHGMgd4xG8BeW2H5qnQ"
        const val TYPE = "(cities)"


        fun onStart(): GoogleMapsAPI{
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://maps.googleapis.com/maps/api/place/")
                    .build()
            return retrofit.create(GoogleMapsAPI::class.java)
        }
    }

    @GET("autocomplete/json")
    fun getSurgestions(
            @Query("types") types: String,
            @Query("input") input: String,
            @Query("key") key: String
    ): Call<AutocompletePOJO>

    @GET("details/json")
    fun getCityDetails(
            @Query("placeid") placeId: String,
            @Query("key") key: String): Call<DetailsPOJO>
}