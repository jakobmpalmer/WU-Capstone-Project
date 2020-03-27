package com.example.willamette_thesis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_transpo.*
import java.util.*

class TranspoDataFragment : Fragment() {

        private val TAG = "Navigation Error"

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


            val appHome = HomeActivity()
            val dateToday = appHome.getOurDate()
            val timeNow = appHome.getOurTime()

            val db = FirebaseFirestore.getInstance()
            val userPath = (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
            //val travelRef = db.collection(userPath).document("travel-data")
            val dateRef = db.collection(userPath).document(dateToday).collection(timeNow)
            val travelRef = dateRef.document("travel-data")
            // ABOVE to be implemented with date select

            val daysData = travelRef.get()
            println(message = "travel Dta $daysData")



            travelRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        var carData = (document.data?.get("car_data")?.toString() ?: "0")
                        var busData = (document.data?.get("bus_data")?.toString() ?: "0")
                        var airData = (document.data?.get("plane_data")?.toString() ?: "0")
                        var walkData = (document.data?.get("walk_data")?.toString() ?: "0")

                        var carTotal = carData.toFloat()
                        var busTotal = busData.toFloat()
                        var airTotal = airData.toFloat()
                        var walkTotal = walkData.toFloat()

                        var totalMiles = carTotal + busTotal + airTotal + walkTotal

                        totalMilesText.text = totalMiles.toString() + "miles"
                        carbonFootrpintText.text = calculateCarbonFPCar(totalMiles).toString() + " C02e"
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }

            return inflater.inflate(R.layout.fragment_transpo, container, false)
        }

    /*
    Vehicle : distance (km/yr) /*EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Bus : distance (km/yr) * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Metro: distance (km/yr) * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Taxi: distance (km/yr) * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Rail: distance (km/yr) * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Flying : distance (km/yr)* 1.09 * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    */*/

//    fun getOurDate() : String{
//        var ourYear = calendar.get(Calendar.YEAR)
//        var ourMonth = calendar.get(Calendar.MONTH)
//        var ourDay = calendar.get(Calendar.DAY_OF_MONTH)
//
//        return ("$ourYear, $ourMonth, $ourDay")
//    }
//
//    private fun getOurTime() : String{
//        var ourHour = calendar.get(Calendar.HOUR_OF_DAY)
//        var ourMin = calendar.get(Calendar.MINUTE)
//        var ourSec = calendar.get(Calendar.SECOND)
//        var ourMilisec = calendar.get(Calendar.MILLISECOND)
//
//        return ("$ourHour, $ourMin, $ourSec, $ourMilisec")
//    }

    private fun calculateCarbonFPCar(miles: Float) : Float{
        return miles * 8.31f
    }




}