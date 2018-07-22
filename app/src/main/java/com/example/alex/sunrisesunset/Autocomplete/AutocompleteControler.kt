package com.example.alex.sunrisesunset.Autocomplete

import android.util.Log
import com.example.alex.sunrisesunset.GoogleMapsAPI
import com.example.alex.sunrisesunset.GoogleMapsAPI.Companion.API_KEY
import com.example.alex.sunrisesunset.GoogleMapsAPI.Companion.TYPE
import com.example.alex.sunrisesunset.MainCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AutocompleteControler(clbck: MainCallback): Callback<AutocompletePOJO> {



    val TAG = "MyTagAutoControler"
    val callback = clbck

    lateinit var retrofit: GoogleMapsAPI
    lateinit var call: Call<AutocompletePOJO>


    fun onStart(): GoogleMapsAPI {
        retrofit = GoogleMapsAPI.onStart()
        return retrofit
    }

    fun sendRequest(text: String) {
        Log.d(TAG, "sendAutocompleteRequest")
        call = retrofit.getSurgestions( TYPE, text, API_KEY)
        call.enqueue(this)
    }

    override fun onFailure(call: Call<AutocompletePOJO>?, t: Throwable?) {
        Log.d(TAG, "onFailure ${t?.message}")
    }

    override fun onResponse(call: Call<AutocompletePOJO>?, response: Response<AutocompletePOJO>?) {
        Log.d(TAG, "onResponse")
        val predictions = response?.body()?.getPredictions()
        if(predictions == null) return
        Log.d(TAG, predictions.toString())
        callback.autocompleteCallback(predictions)
    }

}