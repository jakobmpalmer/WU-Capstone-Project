package com.example.willamette_thesis

//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_car.*


class CarActivity : AppCompatActivity() {

    //private lateinit var database: DatabaseReference
    private val db = FirebaseFirestore.getInstance()
    //add the tag
    val TAG: String = "ECO-FR3ndly"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        val submitButton = findViewById<Button>(R.id.button1)

        submitButton.setOnClickListener{ it: View? ->
            //var input = car_input.toString().toDouble()
            Toast.makeText(this@CarActivity, "Transportation info for the day has been recorded", LENGTH_SHORT).show()

            //var ourData = getTranspoData()
            //storeData("DATE", ourData)
            //storeData("DATE", "[0,0,125]")
            //storeData(LocalDateTime.now().toString(), getTranspoData()) //REQUIRES API MIN 26 --Current 24
            storeData("TEMP-DATE")
        }

        //database = FirebaseDatabase.getInstance().reference

    }


// REALTIME METHOD OF STORAGE
//    private fun storeData(someDate: String){ // Change someData to an intarray?
//
//        val ref = FirebaseDatabase.getInstance().getReference("transpo-data") // Refrence to database
//        println("Got REF")
//
//        val car_data = if (car_text.text != null) car_text.text.toString().toInt() else 0
//        val bus_data = if (bus_text.text != null) bus_text.text.toString().toInt() else 0
//        val plane_data = if (plane_text.text != null) plane_text.text.toString().toInt() else 0
//        val walk_data = if (walk_text.text != null) walk_text.text.toString().toInt() else 0
//        //database.child("users").child(userId).setValue(user)
//
//        //database.child("server/saving-data/transpo-data").child()
//        val dataId = ref!!.push().key!!
//        println("Got dataID")
//        val tData = TranspoData(someDate, car_data, bus_data, plane_data, walk_data)
//        println("Got tData")
//        ref.child(dataId).setValue(tData)
//        println("Done")
//    }

/* Method used to write user data to the cloud firestore
*  2 / 24 / 2020
*
* */
    private fun storeData(someDate: String) {
        val carData = if (car_text.text != null) car_text.text.toString().toInt() else 0
        val busData = if (bus_text.text != null) bus_text.text.toString().toInt() else 0
        val planeData = if (plane_text.text != null) plane_text.text.toString().toInt() else 0
        val walkData = if (walk_text.text != null) walk_text.text.toString().toInt() else 0


        val data = hashMapOf(
            "car_data" to carData,
            "bus_data" to busData,
            "plane_data" to planeData,
            "walk_data" to walkData
        )

// Add a new document with a generated ID
        db.collection("/travel-data")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }





}
