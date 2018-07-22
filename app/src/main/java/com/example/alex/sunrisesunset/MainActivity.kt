package com.example.alex.sunrisesunset

import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.example.alex.sunrisesunset.Autocomplete.AutocompleteControler
import com.example.alex.sunrisesunset.Autocomplete.Predictions
import com.example.alex.sunrisesunset.CityDetails.DetailsControler
import com.example.alex.sunrisesunset.CityDetails.DetailsResult
import com.example.alex.sunrisesunset.R.id.*
import com.example.alex.sunrisesunset.sunriseSunset.Results
import com.example.alex.sunrisesunset.sunriseSunset.SunriseSunsetControler
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity(), MainCallback {

    val TAG = "MyTagMainActivity"

    var coords: Pair<Double, Double> = 0.0 to 0.0

    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var sunriseView: TextView
    lateinit var sunsetView: TextView
    lateinit var SScontroler: SunriseSunsetControler
    lateinit var ACcontroler: AutocompleteControler
    lateinit var DTcontroler: DetailsControler
    lateinit var titleView: TextView
    lateinit var autocompleteView: AutoCompleteTextView
    lateinit var locationTask: Task<Location>
    lateinit var arAdapter: ArrayAdapter<String>
    var predictions: List<Predictions> = ArrayList<Predictions>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initSun()
        initLocation()
        initAuto()

    }

    private fun initAuto() {
        ACcontroler = AutocompleteControler(this)
        DTcontroler = DetailsControler(this)
        DTcontroler.initRetrofit(ACcontroler.onStart())

    }

    private fun initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val check = ContextCompat.checkSelfPermission(baseContext, android.Manifest.permission.ACCESS_FINE_LOCATION)
        if(check == PackageManager.PERMISSION_GRANTED){
            locationTask = fusedLocationClient.lastLocation
            getLastLocation()
        }
    }

    private fun getLastLocation() {
        locationTask.addOnSuccessListener(object: OnSuccessListener<Location>{
            override fun onSuccess(p0: Location?) {
                Log.d(TAG, "lastLocation onSuccesListener: ${p0.toString()}")
                if(p0 != null) {
                    requestSunInfo(p0?.latitude to p0?.longitude)
                }
            }
        })
    }

    private fun requestSunInfo(pair: Pair<Double, Double>) {
        SScontroler.onStart(pair)
    }

    private fun initSun() {
        SScontroler = SunriseSunsetControler(this)
    }

    private fun initViews() {
        sunriseView = findViewById(R.id.sunrise_view)
        sunsetView = findViewById(R.id.sunset_view)
        titleView = findViewById(R.id.title_view)
        setSupportActionBar(findViewById(R.id.toolbar))
        autocompleteView = findViewById(R.id.autocomplete_view)
        arAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line)
        autocompleteView.setAdapter(arAdapter)
        autocompleteView.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                Log.d(TAG, text)
                ACcontroler.sendRequest(text)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        autocompleteView.setOnItemClickListener(object: AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position >= 0 && predictions.size > position) {
                    val prediction = predictions[position]
                    Log.d(TAG, "onItemCliclListener position = $position, " +
                            "name = ${prediction.description}, " +
                            "id = ${prediction.place_id}")
                    DTcontroler.sendRequest(prediction.place_id)
                }
            }
        })
    }

    override fun suntiseSunsetCallback(results: Results) {
        sunriseView.text = "Sunrise at: ${results.sunrise}"
        sunsetView.text = "Sunset at: ${results.sunset}"
    }

    override fun autocompleteCallback(predictions: List<Predictions>) {
        Log.d(TAG, "autocompleteCallback")
        this.predictions = predictions
        arAdapter.clear()
        for(prediction in predictions){
            arAdapter.add(prediction.description)
        }
        arAdapter.notifyDataSetChanged()
    }

    override fun sityDetailsCallback(result: DetailsResult) {
        val location = result.geometry.location
        coords = location.lat to location.lng
        Log.d(TAG, "DetailsResult = ${result}")
        SScontroler.sendRequest(coords)
        titleView.text = "Sunrise Sunset data of ${predictions[0].description}"
    }
}
