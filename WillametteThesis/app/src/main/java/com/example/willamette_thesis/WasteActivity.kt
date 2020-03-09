package com.example.willamette_thesis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_waste.*

class WasteActivity : AppCompatActivity() {



    private val db = FirebaseFirestore.getInstance()
    //add the tag
    val TAG: String = "ECO-FR3ndly"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waste)
        //val binding = R.layout.ActivityWasteBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        val submitWasteBtn = findViewById<Button>(R.id.submitWaste_Button)
        submitWasteBtn.setOnClickListener {
            //var data = getData()
            Toast.makeText(this@WasteActivity, "Waste info for the day has been recorded", Toast.LENGTH_SHORT).show()
            storeData("Date")
        }
    }

    private fun storeData(someDate: String) {
        // Create a new user with a first and last name
        val plastic_data = if (plastic_text.text != null) plastic_text.text.toString().toInt() else 0
        val recycle_data = if (recycle_text.text != null) recycle_text.text.toString().toInt() else 0
        val trash_data = if (trash_text.text != null) trash_text.text.toString().toInt() else 0


        val data = hashMapOf(
            "plastic_data" to plastic_data,
            "recycle_data" to recycle_data,
            "trash_data" to trash_data
        )
// Add a new document with a generated ID
        val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
        //db.collection("/waste-data")
//        db.collection(userPath)
//            .add(data)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
        db.collection(userPath).document("/waste-data").set(data)
    }


}
