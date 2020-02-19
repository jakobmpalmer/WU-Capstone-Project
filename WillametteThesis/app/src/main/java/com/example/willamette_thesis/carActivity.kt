package com.example.willamette_thesis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.EditTextPreference
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT


class carActivity : AppCompatActivity() {

    val car_list = mutableListOf<Double>()  // List for miles on car


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        val submitButton = findViewById<Button>(R.id.button1)

        val car_input= findViewById <EditText> (R.id.editText) as Double   // Input on car


        submitButton.setOnClickListener{ it: View? ->
            Toast.makeText(this@carActivity, "Transportation info for the day has been recorded", LENGTH_SHORT).show()

            car_list += car_input  // Adding daily miles on car to list



        }
    }
}
