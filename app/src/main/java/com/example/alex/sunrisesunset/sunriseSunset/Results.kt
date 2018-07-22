package com.example.alex.sunrisesunset.sunriseSunset

data class Results(
    var day_length: String,

    var sunset: String,

    var nautical_twilight_begin: String,

    var solar_noon: String,

    var astronomical_twilight_end: String,

    var civil_twilight_end: String,

    var astronomical_twilight_begin: String,

    var nautical_twilight_end: String,

    var sunrise: String,

    var civil_twilight_begin: String){

    override fun toString(): String {
        return "ClassPojo [day_length = $day_length, sunset = $sunset, nautical_twilight_begin = $nautical_twilight_begin, solar_noon = $solar_noon, astronomical_twilight_end = $astronomical_twilight_end, civil_twilight_end = $civil_twilight_end, astronomical_twilight_begin = $astronomical_twilight_begin, nautical_twilight_end = $nautical_twilight_end, sunrise = $sunrise, civil_twilight_begin = $civil_twilight_begin]"
    }
}