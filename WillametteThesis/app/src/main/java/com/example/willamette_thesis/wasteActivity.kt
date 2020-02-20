package com.example.willamette_thesis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.firestore.FirebaseFirestore

class wasteActivity : AppCompatActivity() {



    private val db = FirebaseFirestore.getInstance()
    //add the tag
    val TAG: String = MainActivity::class.java.getSimpleName()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waste)

        val submitWasteBtn = findViewById<Button>(R.id.submitWaste_Button)
        submitWasteBtn.setOnClickListener {
            //var data = getData()
            storeData("Date", "Data")
        }
    }







    private fun storeData(someDate: String, someData: String) {
        // Create a new user with a first and last name
        val data = hashMapOf(
            "date" to someDate,
            "Data" to someData
        )
        val data2 = hashMapOf(
            "date" to someDate,
            "Data" to someData,
            "PlaneMiles" to "20"
        )

// Add a new document with a generated ID
        db.collection("/waste-data")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }


        // Add a new document with a generated ID
        db.collection("/waste-data")
            .add(data2)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }


    }
}
