package com.example.alex.sunrisesunset.sunriseSunset

class SunriseSunsetPOJO(){
    private var results: Results? = null

    private var status: String? = null

    fun getResults(): Results? {
        return results
    }

    fun setResults(results: Results) {
        this.results = results
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    override fun toString(): String {
        return "ClassPojo [results = $results, status = $status]"
    }
}