package com.example.willamette_thesis

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_car.*
import java.util.*

class CarActivity2 : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
    private val PREF_FILE = "com.theme.prefs"
    private val PREF_THEME = "theme-preference"

    private val PREF_PROF_FILE = "com.profile.prefs"
    private val PREF_MPG = "profile-pref-mpg"
    private val PREF_FUEL = "profile-pref-fuel"

    //add the tag
    val TAG: String = "ECO-FR3ndly"


    private var ids: Array<String?>? = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000)
    private var pdt: SimpleTimeZone = SimpleTimeZone(-8 * 60 * 60 * 1000, ids?.get(0))
    private var calendar: Calendar = GregorianCalendar(this.pdt)


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeTheme()
        setContentView(R.layout.activity_car)

        // if day is not in firebase reset inputPrefs

        val submitButton = findViewById<Button>(R.id.button1)
        submitButton.setOnClickListener{ it: View? ->
            storeData(getOurDate())
            Toast.makeText(this@CarActivity2, "Transportation info for the day has been recorded", Toast.LENGTH_SHORT).show()
        }

    }

    fun changeTheme(){
        val sharedPref = this.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE) ?: return
        val name = sharedPref.getString(PREF_THEME, "original")
        if (name == "nature") {
            setTheme(R.style.Blue)
        } else if (name == "original") {
            setTheme(R.style.AppTheme)
        }
    }

    fun getPref(sharedPref: SharedPreferences,pref_to_retrieve: String, default : Float) : Float{
        val value_retrieved = sharedPref.getFloat(pref_to_retrieve, default)
        return value_retrieved
    }

    fun getOurDate() : String{
        var ourYear = calendar.get(Calendar.YEAR)
        var ourMonth = calendar.get(Calendar.MONTH)
        var ourDay = calendar.get(Calendar.DAY_OF_MONTH)

        return ("$ourYear, $ourMonth, $ourDay")
    }

    fun savePrefInput(prefName:String, input:Float){
        val prefs = this.getSharedPreferences("com.prevCarVals.prefs", Context.MODE_PRIVATE)
        val editor = prefs!!.edit()
        editor.putFloat(prefName, input)
        editor.apply()
    }

    fun storeData(ourDate: String){

        var carData = if (car_text.text.isNotEmpty()) car_text.text.toString().toDouble() else 0.0
        var busData = if (bus_text.text.isNotEmpty()) bus_text.text.toString().toDouble() else 0.0
        var planeData = if (plane_text.text.isNotEmpty()) plane_text.text.toString().toDouble() else 0.0
        var walkData = if (walk_text.text.isNotEmpty()) walk_text.text.toString().toDouble() else 0.0


        db.collection(userPath).document(ourDate).collection("transportation").document(ourDate).get().addOnSuccessListener {result ->

            carData += result?.get("car_miles").toString().toDouble()
            busData += result?.get("bus_miles").toString().toDouble()
            planeData += result?.get("plane_miles").toString().toDouble()
            walkData += result?.get("walk_miles").toString().toDouble()


            savePrefInput("car", carData.toFloat())
            savePrefInput("bus", busData.toFloat())
            savePrefInput("plane", planeData.toFloat())
            savePrefInput("walk", walkData.toFloat())

        }

        val sharedPref = this.getSharedPreferences(PREF_PROF_FILE, Context.MODE_PRIVATE) ?: return
        val mpg = getPref(sharedPref, PREF_MPG,19.73F)
        val fuel = getPref(sharedPref, PREF_FUEL,21.25F)

        val sharedPrefInput = this.getSharedPreferences("com.prevCarVals.prefs", Context.MODE_PRIVATE) ?: return
        val car = getPref(sharedPrefInput, "car",0F).toDouble()
        val bus = getPref(sharedPrefInput, "bus",0F).toDouble()
        val plane = getPref(sharedPrefInput, "plane",0F).toDouble()
        val walk = getPref(sharedPrefInput, "walk",0F).toDouble()


        val carb_footprint = calcImpact(car,bus,plane,walk,fuel,mpg)

        val data = hashMapOf(
            "car_miles" to car,
            "bus_miles" to bus,
            "plane_miles" to plane,
            "walk_miles" to walk,
            "carb_footprint" to carb_footprint
        )

        db.collection(userPath).document(ourDate).collection("transportation").document(ourDate).set(data)

    }


    fun calcImpact(carData:Double, busData:Double , planeData:Double  , walkData:Double ,fuel_rate : Float = 21.25F, miles_per_gal : Float = 19.73F): Double {
        // fuel rate is the standard rate of CO2 commissions for fuel. 21.25 is the average of petrol (20lbs)
        // and diesel (22.5). If we get the type of fuel the user user we can make if more accurate.
        // miles_per_gal is how many miles a car can go with a gallon of fuel. Depends on brand and make of car.
        // we are using an average as default (21.3), but can make it more precise if need be.

        val flight_hours = planeData/565
        // 565 is the average num of miles in an hour
        val flight_type = if (flight_hours >= 4) 4400 else 1100


        val carEmission = ((carData-walkData)/miles_per_gal) * fuel_rate

        println("EQ=:: ( $carData - $walkData / $miles_per_gal ) * $fuel_rate")
        println("carEmission: $carEmission")

        // taking walkData from carData, as we make the assumption that walking is a replacement for using a car
        val busEmission = ((busData/12.52) * 22.5) / 60
        println("busEmission: $busEmission")
        // Assumption that buses use diesel, hence the fuel rate used is 22.5. We divide by 60 as on average
        // a bus has an av seating capacity of 40-80, so we average that out
        // 12.52 is used as miles per gal, as we assume that it is a Passenger Van
        val planeEmission = planeData * flight_type * 21.25
        println("planeEmission: $planeEmission")
        // 21.25 is average on fuel types, as users are unlikely to have this info

        return (carEmission + busEmission + planeEmission)

    }
}
