package com.example.alex.sunrisesunset

import com.example.alex.sunrisesunset.Autocomplete.Predictions
import com.example.alex.sunrisesunset.CityDetails.DetailsResult
import com.example.alex.sunrisesunset.sunriseSunset.Results

interface MainCallback {
    fun suntiseSunsetCallback(results: Results)
    fun autocompleteCallback(predictions: List<Predictions>)
    fun sityDetailsCallback(result: DetailsResult)
}