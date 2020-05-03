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
            val dateDoc = db.collection(userPath).document(dateToday) // Doc of data for todays date
            val travelCollection = dateDoc.collection("travel-data") //Travel data for the day

            val totalRef = db.collection(userPath).document(dateToday).collection("total-data")
            //val travelTotalRef = totalRef.document("travel-total")
            val travelTotalRef = db.collection(userPath).document(dateToday).collection("transportation").document(dateToday)
            val consumpTotalRef = totalRef.document("consump-total")
            val wasteTotalRef = totalRef.document("waste-total")
            // val travelRef = db.collection(userPath).document("travel-data")
           // val wasteRef = db.collection(userPath).document("waste-data")
           // val consumpRef = db.collection(userPath).document("consumable-data")

//            val tabs = todayTabLayout
//            //val calendarTab = calendarTab
//
//            val dataLayout = todayTabLayout
//            dataLayout.addTab(dataLayout.newTab().setText("Today!"));
//            dataLayout.addTab(dataLayout.newTab().setText("Calendar"));




            travelTotalRef.get().addOnSuccessListener { result ->

                var carTotalValue = if (result.get("car_miles") != null) result.get("car_miles").toString().toFloat() else 0f
                var busTotalValue = if (result.get("bus_miles") != null) result.get("bus_miles").toString().toFloat() else 0f
                var planeTotalValue = if (result.get("plane_miles") != null) result.get("plane_miles").toString().toFloat() else 0f
                var walkTotalValue = if (result.get("walk_miles") != null) result.get("walk_miles").toString().toFloat() else 0f

                carTotalVar.text = carTotalValue.toString()
                busTotalVar.text = busTotalValue.toString()
                planeTotalVar.text = planeTotalValue.toString()
                walkTotalVar.text = walkTotalValue.toString()

                //var totalMiles = if (result.get("sum_miles") != null) result.get("sum_total").toString().toFloat() else 0f
                //totalMilesVar.text = if(totalMiles != null) ("$totalMiles miles") else 0.toString()
                var totalMiles = carTotalValue + busTotalValue + planeTotalValue + walkTotalValue
                totalMilesVar.text = totalMiles.toString()
                var totalKm: Float = totalMiles * 1.61.toFloat()
                totalKmVar.text = ("$totalKm Kilometers")


                //var totalCarbonFp = if(result.get("carbon_fp_sum")!= null) result.get("carbon_fp_sum") else 0f
                var totalCarbonFp = if(result.get("carb_footprint")!= null) result.get("carb_footprint") else 0f
                totalCarbonFp = totalCarbonFp
                carbonFootrpintVar.text = ("$totalCarbonFp C02e")

            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting travel total: ", exception)
            }

            consumpTotalRef.get().addOnSuccessListener { result ->
                val totalConsump = if (result.get("sum_total") != null) result.get("sum_total").toString().toFloat() else 0f
                totalMeatVar.text = ("$totalConsump lbs")

                val cowTotalValue: Float = if (result.get("cow_total") != null) result.get("cow_total").toString().toFloat() else 0f
                val pigTotalValue: Float = if (result.get("pig_total") != null) result.get("pig_total").toString().toFloat() else 0f
                val chickenTotalValue: Float = if (result.get("chicken_total") != null) result.get("chicken_total").toString().toFloat() else 0f

                cowTotalVar.text = cowTotalValue.toString()
                pigTotalVar.text = pigTotalValue.toString()
                chickenTotalVar.text = chickenTotalValue.toString()
            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting Consumption Total: ", exception)
            }

            wasteTotalRef.get().addOnSuccessListener { result ->
                var totalWaste = if (result.get("sum_total") != null) result.get("sum_total").toString().toFloat() else 0f
                totalWasteVar.text = ("$totalWaste lbs")

                var plasticTotalValue = if (result.get("plastic_total") != null) result.get("plastic_total").toString().toFloat() else 0f
                var recycleTotalValue = if (result.get("recycle_total") != null) result.get("recycle_total").toString().toFloat() else 0f
                var trashTotalValue = if (result.get("trash_total") != null) result.get("trash_total").toString().toFloat() else 0f

                plasticsTotalVar.text = plasticTotalValue.toString()
                recycleTotalVar.text = recycleTotalValue.toString()
                trashTotalVar.text = trashTotalValue.toString()

            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting Waste Total: ", exception)
            }

            //---------------------------------------------------------------------------------------------------------------

//            val db = FirebaseFirestore.getInstance()
//            val userPath = (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
            println("userPath: $userPath")
            val dateRef = db.collection(userPath).document(dateToday)
            println("dateRef: $dateRef")
            val travelRefDay = dateRef.collection("travel-data")
            println("travelRefDay: $travelRefDay")
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





}