package com.example.willamette_thesis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_car.*
import java.time.LocalDateTime


class CarActivity : AppCompatActivity() {

    private val car_list = mutableListOf<Double>()  // List for miles on car

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        val submitButton = findViewById<Button>(R.id.button1)

        submitButton.setOnClickListener{ it: View? ->
            //var input = car_input.toString().toDouble()
            Toast.makeText(this@CarActivity, "Transportation info for the day has been recorded", LENGTH_SHORT).show()
            //storeData("DATE", getTranspoData())
            storeData("DATE", "[0,0,125]")
            //storeData(LocalDateTime.now().toString(), getTranspoData()) //REQUIRES API MIN 26 --Current 24
        }

        database = FirebaseDatabase.getInstance().reference

    }

    private fun getTranspoData(): Array<Int>{
        val car_data = car_text.text.toString().toInt()
        val bus_data = bus_text.text.toString().toInt()
        val plane_data = plane_text.text.toString().toInt()

        return arrayOf(car_data, bus_data, plane_data)
    }

    private fun storeData(someDate: String, someData: String){ // Change someData to an intarray?

        val ref = FirebaseDatabase.getInstance().getReference("transpo-data") // Refrence to database
        println("Got REF")

        //database.child("users").child(userId).setValue(user)

        //database.child("server/saving-data/transpo-data").child()
        val dataId = ref!!.push().key!!
        println("Got dataID")
        val tData = TranspoData(someDate, someData)
        println("Got tData")
        ref.child(dataId).setValue(tData)
        println("Done")
    }





}
