package com.example.willamette_thesis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_today.*


class TodayDataFragment : Fragment() {

        private val TAG = "Navigation Error"

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            println("Made TDF!?")

            val appHome = HomeActivity()
            val dateToday = appHome.getOurDate()
            val timeNow = appHome.getOurTime()


            val db = FirebaseFirestore.getInstance()
            val userPath = (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
            val dateDoc = db.collection(userPath).document(dateToday) // Doc of dates
            val travelCollection = dateDoc.collection("travel-data") //Travel data for the day
            val travelTotal = dateDoc.collection("travel-data").document("total-data")
            // val travelRef = db.collection(userPath).document("travel-data")
           // val wasteRef = db.collection(userPath).document("waste-data")
           // val consumpRef = db.collection(userPath).document("consumable-data")



            var totalMiles = 0f

            travelCollection
                .get()
                .addOnSuccessListener { result ->
                    println("successful listener")
                    for (doc in result) {
                        Log.d(TAG, "${doc.id} => ${doc.data}")
                        println("Successfully got $doc from collection.. 48")
                        var busData = doc.get("bus_data")
                        var carData = doc.get("car_data")
                        var planeData = doc.get("plane_data")
                        var walkData = doc.get("walk_data")

                        totalMiles = busData.toString().toFloat() + carData.toString().toFloat() + planeData.toString().toFloat() + walkData.toString().toFloat()
                        println("its WORKING!!!!! $totalMiles")
                    }
                    totalMilesVar.text = ("$totalMiles miles")
                    carbonFootrpintText.text = calculateCarbonFPCar(totalMiles).toString() + " C02e"
                    var totalKm = totalMiles * 1.6
                    totalKmVar.text = ("$totalKm Kilometers")
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }




            //---------------------------------------------------------------------------------------------------------------

//            val db = FirebaseFirestore.getInstance()
//            val userPath = (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
            println("userPath: " + userPath)
            val dateRef = db.collection(userPath).document(dateToday)
            println("dateRed: " + dateRef)
            val travelRefDay = dateRef.collection("travel-data")
            println("travelRefDay: " + travelRefDay)
            //val singleEntry = db.collection(userPath).document(dateToday).collection(<"ENTER DESIRED ENTRY TIME">)


//            var carTotal = 0f
//            var busTotal = 0f
//            var airTotal = 0f
//            var walkTotal = 0f





//
//            println("about to make QUery")
//            val travel_query = travelRefDay.whereEqualTo("16, 34, 11, 601", true)
//                .get()
//                .addOnSuccessListener { documents ->
//                    for (document in documents) {
//                        println("Made QUERY")
//                        Log.d(TAG, "${document.id} => ${document.data}")
//
//                        println("\n\nbrakets " + document["car_data"])
//                        println("data " + document.data)
//                        println("get " + document.get("car_data") + "\n\n")
//
//
//                        //for (currentDoc in travelDayData.getResult()!!) { //Visit each document
//                        println("CurrentDoc: " + document.data)
//                        var currentCarData = (document.data?.get("car_data")?.toString() ?: "0")
//                        carTotal = carTotal.toFloat() + currentCarData.toFloat()
//                        var currentBusData = (document.data?.get("bus_data")?.toString() ?: "0")
//                        busTotal = busTotal.toFloat() + currentBusData.toFloat()
//                        var currentAirData = (document.data?.get("plane_data")?.toString() ?: "0")
//                        airTotal = airTotal.toFloat() + currentAirData.toFloat()
//                        var currentWalkData = (document.data?.get("walk_data")?.toString() ?: "0")
//                        walkTotal = walkTotal.toFloat() + currentWalkData.toFloat()
//
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents: ", exception)
//                }
        // Create a query against the collection.
//            val car_query = travelDayDoc.whereEqualTo("car_data", true)
//            val bus_query = travelDayDoc.whereEqualTo("bus_data", true)
//            val air_query = travelDayDoc.whereEqualTo("air_data", true)
//            val walk_query = travelDayDoc.whereEqualTo("walk_data", true)

            // ABOVE to be implemented with date select

            //val daysData = travelRef.get()
            //val daysData = travelRefDay.get()
            //println(message = "travel Dta $daysData")


            //val travelDayCollect = travelRefDay
            //val travelDayData = travelRefDay.get()
//            if (travelDayData.isSuccessful()) {
//                println("In travelDayData!!@")

//                for (currentDoc in travelRefDay) { //Visit each document
//                    //currentDoc.data
//    //                .addOnSuccessListener { document ->
//    //                    if (document != null) {
//    //                        Log.d(TAG, "DocumentSnapshot data: ${travelRefDay.document().get()}")
//    //                val travelDayData = travelRefDay.get()
//    //                if (travelDayData.isSuccessful()) {
//    //                    println("In travelDayData!!@")
//                    println("\n\nbrakets " + currentDoc["car_data"])
//                    println("data " + currentDoc.data)
//                    println("get " + currentDoc.get("car_data") + "\n\n")
//
//
//                        //for (currentDoc in travelDayData.getResult()!!) { //Visit each document
//                        println("CurrentDoc: " + currentDoc.data)
//                        var currentCarData = (currentDoc.data?.get("car_data")?.toString() ?: "0")
//                        carTotal = carTotal.toFloat() + currentCarData.toFloat()
//                        var currentBusData = (currentDoc.data?.get("bus_data")?.toString() ?: "0")
//                        busTotal = busTotal.toFloat() + currentBusData.toFloat()
//                        var currentAirData = (currentDoc.data?.get("plane_data")?.toString() ?: "0")
//                        airTotal = airTotal.toFloat() + currentAirData.toFloat()
//                        var currentWalkData = (currentDoc.data?.get("walk_data")?.toString() ?: "0")
//                        walkTotal = walkTotal.toFloat() + currentWalkData.toFloat()
//                    }

                            //var totalMiles = carTotal + busTotal + airTotal + walkTotal

                            //totalMilesText.text = totalMiles.toString() + "miles"


//            } else { // else -- if document is null
//                Log.d(
//                    TAG,
//                    "TDD. Error getting documents: ",
//                    travelDayData.getException()
//                )
//            }



            //travelRef.get()
//                .addOnSuccessListener { document ->
//                    if (document != null) {
//                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                        var carData = (document.data?.get("car_data")?.toString() ?: "0")
//                        var busData = (document.data?.get("bus_data")?.toString() ?: "0")
//                        var airData = (document.data?.get("plane_data")?.toString() ?: "0")
//                        var walkData = (document.data?.get("walk_data")?.toString() ?: "0")
//
//                        var carTotal = carData.toFloat()
//                        var busTotal = busData.toFloat()
//                        var airTotal = airData.toFloat()
//                        var walkTotal = walkData.toFloat()
//
//                        var totalMiles = carTotal + busTotal + airTotal + walkTotal
//
//                        totalMilesText.text = totalMiles.toString() + "miles"
//                        carbonFootrpintText.text = calculateCarbonFPCar(totalMiles).toString() + " C02e"
//                    } else {
//                        Log.d(TAG, "No such document")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.d(TAG, "get failed with ", exception)
//                }

            return inflater.inflate(R.layout.fragment_today, container, false)
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

    private fun sumDay(): Float {
        var sumMiles = 0f

        return sumMiles
    }

    private fun calculateCarbonFPCar(miles: Float) : Float{
        return miles * 8.31f
    }




}