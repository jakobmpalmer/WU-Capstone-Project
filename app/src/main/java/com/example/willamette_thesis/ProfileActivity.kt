package com.example.willamette_thesis

import android.app.Activity
import java.io.File
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.profile_activity.*
import java.util.*

class ProfileActivity  : AppCompatActivity(){

    private val db = FirebaseFirestore.getInstance()
    //add the tag
    val TAG: String = "ECO-FR3ndly"

    val appHome = HomeActivity()

    lateinit var choose_car : Spinner
    lateinit var chosen_mpg : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setTheme(R.style.Green)
        setContentView(R.layout.profile_activity)

        //database = FirebaseDatabase.getInstance().reference

        choose_car = findViewById(R.id.spinnerCar) as Spinner
        chosen_mpg = findViewById(R.id.milesPerGallon) as TextView

        userEmailText.text = "Email: " + appHome.getUserEmail()


        ArrayAdapter.createFromResource(
            this,
            R.array.carTypes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            choose_car.adapter = adapter
        }


}
}
