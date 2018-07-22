package com.example.alex.sunrisesunset.sunriseSunset

import android.util.Log
import com.example.alex.sunrisesunset.MainCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SunriseSunsetControler(cllbck: MainCallback): Callback<SunriseSunsetPOJO> {

    val TAG = "MyTagControler"
    val callback = cllbck

    lateinit var retrofit: SunriseSunsetApi
    lateinit var call: Call<SunriseSunsetPOJO>

    fun onStart(coords: Pair<Double, Double>){
        retrofit = SunriseSunsetApi.create()
        sendRequest(coords)
    }

    fun sendRequest(coords: Pair<Double, Double>){
        call = retrofit.getCoords(coords.first, coords.second)
        call.enqueue(this)
    }

    override fun onFailure(call: Call<SunriseSunsetPOJO>?, t: Throwable?) {
        Log.d(TAG, "onFailure ${t?.message}")
    }

    override fun onResponse(call: Call<SunriseSunsetPOJO>?, response: Response<SunriseSunsetPOJO>?) {
        if(response == null) return
        val results = response.body()?.getResults()
        if(results == null) return
        callback.suntiseSunsetCallback(results)
    }
}