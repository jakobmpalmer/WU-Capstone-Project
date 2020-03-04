package com.example.willamette_thesis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore


class AnalyticsActivity : AppCompatActivity(){

    //lateinit var toolbar: ActionBar
    private val db = FirebaseFirestore.getInstance()
    //var user = firebase.auth().currentUser;
    val TAG = "Analytics Problem"

//    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when (item.itemId) {
//            R.id.navigation_waste -> {
//                //toolbar.title= "Waste"
//                val wasteFragment = WasteDataFragment.newInstance()
//                openFragment(wasteFragment)
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_transportation -> {
//                //toolbar.title= "Transportation"
//                val transpoFragment = WasteDataFragment.newInstance()
//                openFragment(transpoFragment)
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_consumable -> {
//                //toolbar.title= "Consumable"
//                val consumableFragment = WasteDataFragment.newInstance()
//                openFragment(consumableFragment)
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }

//    private fun openFragment(fragment: Fragment) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.container, fragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)
        //setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayShowTitleEnabled(false)
            //setUpNavigation()
        //toolbar = supportActionBar!!

        val bottomNavigation: BottomNavigationView = findViewById(R.id.dataNavigationView)
        val navController: NavController = Navigation.findNavController(this, R.id.home_fragment)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

        //bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

//        val userPath = (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
//        val travelRef = db.collection(userPath).document("travel-data")
//        val wasteRef = db.collection(userPath).document("waste-data")
//        val consumpRef = db.collection(userPath).document("consumable-data")
//
//        travelRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                    gasTotalTV.text = (document.data?.get("bus_data")?.toString() ?: "NULL_VALUE") + " miles"
//                } else {
//                    Log.d(TAG, "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//            }
//
//        wasteRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                    wasteTotalTV.setText((document.data?.get("plastic_data")?.toString() ?: "NULL_VALUE") + " plastic")
//
//                } else {
//                    Log.d(TAG, "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//            }
//
//        consumpRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                    waterTotalTV.setText((document.data?.get("water_data")?.toString() ?: "NULL_VALUE") + " water")
//                    waterWasteTV.text = calcWaterWaste(document.data?.get("water_data")?.toString() ?: "NULL_VALUE").toString() + " li/yr"
//                } else {
//                    Log.d(TAG, "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//            }



        //gasTotalTV.setText(travelRef.get().toString() + " miles")
        //wasteTotalTV.setText(wasteRef.toString() + " waste")
        //waterTotalTV.setText(waterRef.firestore.toString() + " water")
    } // On Create

//    fun setUpNavigation() {
//        val bottomNav = findViewById<BottomNavigationView>(R.id.dataNavigationView)
//        //bottomNav = findViewById(R.id.dataNavigationView)
//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.fragment_waste) as NavHostFragment?
//        NavigationUI.setupWithNavController(
//            bottomNav,
//            navHostFragment!!.navController
//        )
//    }
//
//    /* Function used to calculate the water emissions based off the given usage.
//    Water : use (litres/day) * 365 * EF (kg CO2e/kWh) = emissions (kg CO2e/yr) */
//    private fun calcWaterWaste(waterUse : String) : Float {
//        var useFloat = 0f
//        useFloat = if (waterUse != "NULL_VALUE") waterUse.toFloat() else 0f
//        return useFloat * 365
//    }


}