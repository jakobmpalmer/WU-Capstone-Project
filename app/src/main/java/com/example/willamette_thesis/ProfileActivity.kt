package com.example.willamette_thesis

import android.content.Context
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
import java.util.*


class ProfileActivity  : AppCompatActivity(){

    private val db = FirebaseFirestore.getInstance()
    //add the tag
    val TAG: String = "ECO-FR3ndly"

    private var ids: Array<String?>? = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000)
    private var pdt: SimpleTimeZone = SimpleTimeZone(-8 * 60 * 60 * 1000, ids?.get(0))
    private var calendar: Calendar = GregorianCalendar(this.pdt)

    val appHome = HomeActivity()

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
        //setTheme(R.style.Green)
        setContentView(R.layout.profile_activity)

        //database = FirebaseDatabase.getInstance().reference

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

            saveMileageSelected(indexCar)
            saveFuelSelected(indexFuel)
            updateTextViews()

        }

        updateTextViews()


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
        //val mpg = findViewById(R.id.milesPerGallon) as TextView
        //val mpg_double = mpg.text.toString().toDouble()

        val res : Resources = resources
        val data_mpg = res.getStringArray(R.array.carMileage).get(p2).toDouble()

        //val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")

        val data = hashMapOf("mpg" to data_mpg)
        db.collection(userPath).document("mileage_selected").set(data)
        db.collection(userPath).document("car_selected").set(hashMapOf("carType" to saveCar))

    }

    fun saveFuelSelected(p2:Int) {
        //val mpg = findViewById(R.id.milesPerGallon) as TextView
        //val mpg_double = mpg.text.toString().toDouble()

        val res : Resources = resources
        val fuel = res.getStringArray(R.array.fuelRate).get(p2).toDouble()

        //val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")

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

}




