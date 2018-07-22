package com.example.alex.sunrisesunset.Autocomplete

class AutocompletePOJO {
    private var predictions: List<Predictions>? = null

    private var status: String? = null

    fun getPredictions(): List<Predictions>? {
        return predictions
    }

    fun setPredictions(predictions: List<Predictions>) {
        this.predictions = predictions
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    override fun toString(): String {
        return "ClassPojo [results = $predictions, status = $status]"
    }
}