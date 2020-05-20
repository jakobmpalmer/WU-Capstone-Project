package com.example.willamette_thesis

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.android.synthetic.main.profile_activity.*
import java.math.RoundingMode
import java.util.*


class ProfileActivity  : AppCompatActivity(){

    private val db = FirebaseFirestore.getInstance()

    private val PREF_FILE = "com.profile.prefs"
    private val PREF_MPG = "profile-pref-mpg"
    private val PREF_FUEL = "profile-pref-fuel"
    private val PREF_CAR = "profile-pref-car"

    private val PREF_THEMES = "com.theme.prefs"

    //add the tag
    val TAG: String = "ECO-FR3ndly"

    private var ids: Array<String?>? = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000)
    private var pdt: SimpleTimeZone = SimpleTimeZone(-8 * 60 * 60 * 1000, ids?.get(0))
    private var calendar: Calendar = GregorianCalendar(this.pdt)

    val appHome = HomeActivity()
    private val ourSettings = SettingsFragment()

    lateinit var choose_car : Spinner
    lateinit var chosen_mpg : TextView
    lateinit var shown_car : TextView
    lateinit var choose_fuel : Spinner
    lateinit var shown_fuel : TextView
    var fuel_selected : Double = 21.25
    var indexCar = 0
    var indexFuel = 0
    var saveCar = ""

    val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val themePrefs = this.getSharedPreferences(PREF_THEMES, Context.MODE_PRIVATE) ?: return
        ourSettings.changeTheme( themePrefs, this)
        setContentView(R.layout.profile_activity)

        choose_car = findViewById(R.id.spinnerCar) as Spinner
        chosen_mpg = findViewById(R.id.milesPerGallon) as TextView
        shown_car = findViewById(R.id.carShown) as TextView
        choose_fuel = findViewById(R.id.fuelSpinner) as Spinner
        shown_fuel = findViewById(R.id.fuelShown) as TextView

        userEmailText.text = appHome.getUserEmail()
        dateCreation.text = appHome.getAccountCreationDate().toString()

        setSpinners()


        val submitButton = findViewById<Button>(R.id.submit_profile)
        submitButton.setOnClickListener{

            val prefs = this.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
            val res : Resources = resources
            val mpg_selected = res.getStringArray(R.array.carMileage).get(indexCar).toFloat()
            val car_selected = res.getStringArray(R.array.carTypes).get(indexCar).toString()
            val fuel_selected = res.getStringArray(R.array.fuelRate).get(indexFuel).toFloat()
            savePrefProfile(prefs, PREF_MPG, mpg_selected)
            savePrefProfile(prefs, PREF_FUEL, fuel_selected)
            savePrefCar(prefs, PREF_CAR,car_selected)

            updateText()

        }
        updateText()

}

    fun setSpinners(){
        ArrayAdapter.createFromResource(
            this,
            R.array.carTypes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            choose_car.adapter = adapter

            choose_car.onItemSelectedListener = object :

                AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    chosen_mpg.text = "None Selected"
                }
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    indexCar = p2
                    val res: Resources = resources
                    saveCar = res.getStringArray(R.array.carTypes).get(p2)
                }
            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.fuelTypes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            choose_fuel.adapter = adapter

            choose_fuel.onItemSelectedListener = object :

                AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    indexFuel = p2

                }
            }
        }

    }

    fun saveMileageSelected(p2:Int) {
        val res : Resources = resources
        val data_mpg = res.getStringArray(R.array.carMileage).get(p2).toDouble()

        val data = hashMapOf("mpg" to data_mpg)
        db.collection(userPath).document("mileage_selected").set(data)
        db.collection(userPath).document("car_selected").set(hashMapOf("carType" to saveCar))

    }

    fun savePrefProfile(prefs: SharedPreferences?, prefName:String, input:Float){
        val editor = prefs!!.edit()
        editor.putFloat(prefName, input)
        editor.apply()
    }
    fun savePrefCar(prefs: SharedPreferences?, prefName:String, input:String){
        val editor = prefs!!.edit()
        editor.putString(prefName, input)
        editor.apply()
    }

    fun saveFuelSelected(p2:Int) {
        val res : Resources = resources
        val fuel = res.getStringArray(R.array.fuelRate).get(p2).toDouble()

        val data = hashMapOf("fuel_rate" to fuel)
        db.collection(userPath).document("fuel_selected").set(data)

    }

    fun updateTextViews(){
        db.collection(userPath).document("mileage_selected").get().addOnSuccessListener {result ->
            chosen_mpg.text = result?.get("mpg").toString().toFloatOrNull().toString()
        }
        db.collection(userPath).document("fuel_selected").get().addOnSuccessListener {result ->
            shown_fuel.text = result?.get("fuel_rate").toString().toFloatOrNull()?.toDouble().toString()
        }
        db.collection(userPath).document("car_selected").get().addOnSuccessListener {result ->
            shown_car.text = result?.get("carType").toString()
        }
    }

    fun updateText(){


        val sharedPref = this.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE) ?: return
        var mpg = sharedPref.getFloat(PREF_MPG, 19.73f).toString().toBigDecimal()
        val fuel = sharedPref.getFloat(PREF_FUEL, 21.25f).toDouble().toString()
        val car = sharedPref.getString(PREF_CAR, "Average")

        mpg = mpg.setScale(2, RoundingMode.HALF_EVEN)

        chosen_mpg.text = mpg.toDouble().toString()
        shown_fuel.text = fuel
        shown_car.text = car
    }

}




