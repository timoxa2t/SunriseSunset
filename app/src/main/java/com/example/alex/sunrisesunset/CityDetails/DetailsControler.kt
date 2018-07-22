package com.example.alex.sunrisesunset.CityDetails

import android.util.Log
import com.example.alex.sunrisesunset.GoogleMapsAPI
import com.example.alex.sunrisesunset.GoogleMapsAPI.Companion.API_KEY
import com.example.alex.sunrisesunset.MainCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsControler(clbck: MainCallback): Callback<DetailsPOJO> {

    val TAG = "MyTagDetailControler"
    val callback = clbck

    lateinit var retrofit: GoogleMapsAPI
    lateinit var call: Call<DetailsPOJO>


    fun initRetrofit(api: GoogleMapsAPI){
        retrofit = api
    }


    fun sendRequest(id: String) {
        call = retrofit.getCityDetails(id, API_KEY)
        call.enqueue(this)
    }

    override fun onFailure(call: Call<DetailsPOJO>?, t: Throwable?) {
        Log.d(TAG, "onFailure ${t?.message}")
    }

    override fun onResponse(call: Call<DetailsPOJO>?, response: Response<DetailsPOJO>?) {
        Log.d(TAG, "onResponce ${response?.body()}")
        val resp = response?.body()?.result
        if(resp == null) return
        callback.sityDetailsCallback(resp)
    }
}