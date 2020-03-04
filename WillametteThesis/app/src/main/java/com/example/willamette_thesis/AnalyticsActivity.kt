package com.example.willamette_thesis

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_analytics.*

class AnalyticsActivity : AppCompatActivity(){

    //lateinit var toolbar: ActionBar
    private val db = FirebaseFirestore.getInstance()
    //var user = firebase.auth().currentUser;
    val TAG = "Analytics Problem"

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_waste -> {
                //toolbar.title= "Waste"
                val wasteFragment = WasteDataFragment.newInstance()
                openFragment(wasteFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_transportation -> {
                //toolbar.title= "Transportation"
                val transpoFragment = WasteDataFragment.newInstance()
                openFragment(transpoFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_consumable -> {
                //toolbar.title= "Consumable"
                val consumableFragment = WasteDataFragment.newInstance()
                openFragment(consumableFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)
        //setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayShowTitleEnabled(false)

        //toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.dataNavigationView)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val userPath = (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
        val travelRef = db.collection(userPath).document("travel-data")
        val wasteRef = db.collection(userPath).document("waste-data")
        val waterRef = db.collection(userPath).document("water-data")
        println("HERHERE: " + db.collection(userPath).document("travel-data").get().toString())
        //println("HERHERE: " + db.collection(userPath).document("travel-data").get().result)

        travelRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    gasTotalTV.text = (document.data?.get("bus_data")?.toString() ?: "NULL_VALUE") + " miles"
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        wasteRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    wasteTotalTV.setText((document.data?.get("plastic_data")?.toString() ?: "NULL_VALUE") + " plastic")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        waterRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    waterTotalTV.setText((document.data?.get("water_data")?.toString() ?: "NULL_VALUE") + " water")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        //gasTotalTV.setText(travelRef.get().toString() + " miles")
        //wasteTotalTV.setText(wasteRef.toString() + " waste")
        //waterTotalTV.setText(waterRef.firestore.toString() + " water")
    }


}