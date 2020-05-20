package com.example.willamette_thesis

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_car.*

class TravelActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")

    private val PREF_PROF_FILE = "com.profile.prefs"
    private val PREF_MPG = "profile-pref-mpg"
    private val PREF_FUEL = "profile-pref-fuel"

    private val PREF_THEMES = "com.theme.prefs"

    //add the tag
    val TAG: String = "ECO-FR3ndly"

    private val appHome = HomeActivity()
    private val ourDate = appHome.getOurDate()
    private val ourSettings = SettingsFragment()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val themePrefs = this.getSharedPreferences(PREF_THEMES, Context.MODE_PRIVATE) ?: return
        ourSettings.changeTheme( themePrefs, this)
        setContentView(R.layout.activity_car)

        val submitButton = findViewById<Button>(R.id.button1)
        submitButton.setOnClickListener{ it: View? ->

            storeData(ourDate)
            Toast.makeText(this@TravelActivity, "Transportation info for the day has been recorded", Toast.LENGTH_SHORT).show()
        }
    }

    fun storeData(ourDate: String){

        var carData = if (car_text.text.isNotEmpty()) car_text.text.toString().toDouble() else 0.0
        var busData = if (bus_text.text.isNotEmpty()) bus_text.text.toString().toDouble() else 0.0
        var planeData = if (plane_text.text.isNotEmpty()) plane_text.text.toString().toDouble() else 0.0
        var walkData = if (walk_text.text.isNotEmpty()) walk_text.text.toString().toDouble() else 0.0

        val sharedPref = this.getSharedPreferences(PREF_PROF_FILE, Context.MODE_PRIVATE) ?: return

        db.collection("users").document(userPath).collection(ourDate).document("transportation").get().addOnSuccessListener {result ->

            carData += if (result?.get("car_miles") != null) result.get("car_miles").toString().toDouble() else 0.0
            busData += if (result?.get("bus_miles") != null) result.get("bus_miles").toString().toDouble() else 0.0
            planeData += if (result?.get("plane_miles") != null) result.get("plane_miles").toString().toDouble() else 0.0
            walkData += if (result?.get("walk_miles") != null) result.get("walk_miles").toString().toDouble() else 0.0

            val mpg = getPref(sharedPref, PREF_MPG,19.73F)
            val fuel = getPref(sharedPref, PREF_FUEL,21.25F)

            val carb_footprint = calcImpact(carData,busData,planeData,walkData,fuel,mpg)

            val data = hashMapOf(
                "car_miles" to carData,
                "bus_miles" to busData,
                "plane_miles" to planeData,
                "walk_miles" to walkData,
                "carb_footprint" to carb_footprint
            )
            db.collection("users").document(userPath).collection(ourDate).document("transportation").set(data)

        }

    }


    fun calcImpact(carData:Double, busData:Double , planeData:Double  , walkData:Double ,fuel_rate : Float = 21.25F, miles_per_gal : Float = 19.73F): Double {
        // fuel rate is the standard rate of CO2 commissions for fuel. 21.25 is the average of petrol (20lbs)
        // and diesel (22.5). If we get the type of fuel the user user we can make if more accurate.
        // miles_per_gal is how many miles a car can go with a gallon of fuel. Depends on brand and make of car.
        // we are using an average as default (21.3), but can make it more precise if need be.

        val flight_hours = planeData/565  // 565 is the average num of miles in an hour
        val flight_type = if (flight_hours >= 4) 4400 else 1100

        val carEmission = ((carData-walkData)/miles_per_gal) * fuel_rate
        // taking walkData from carData, as we make the assumption that walking is a replacement for using a car
        //fuel rate is pounds of CO2 emitted per gallon
        val busEmission = ((busData/12.52) * 22.5) / 60
        // Assumption that buses use diesel, hence the fuel rate used is 22.5. We divide by 60 as on average
        // a bus has an av seating capacity of 40-80, so we average that out
        // 12.52 is used as miles per gal, as we assume that it is a Passenger Van
        val planeEmission = (planeData * flight_type * 21.25)/ 150
        // 21.25 is average on fuel types, as users are unlikely to have this info
        // 150 is average seats in planes (range 150-200), but we picked 150 to be moderate


        //val emissionRounded = BigDecimal(carEmission + busEmission + planeEmission).setScale(2, RoundingMode.HALF_EVEN).toDouble()

        return carEmission + busEmission + planeEmission // in punds of CO2
    }

    fun changeTheme(){
        val sharedPref = this.getSharedPreferences("com.theme.prefs", Context.MODE_PRIVATE) ?: return
        val name = sharedPref.getString("theme-preference", "original")
        if (name == "nature") {
            setTheme(R.style.Orange)
        } else if(name == "dark"){
            setTheme(R.style.Dark)
        }else if (name == "original") {
            setTheme(R.style.AppTheme)
        }
    }

    fun getPref(sharedPref: SharedPreferences,pref_to_retrieve: String, default : Float) : Float{
        val value_retrieved = sharedPref.getFloat(pref_to_retrieve, default)
        return value_retrieved
    }
}
