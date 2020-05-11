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
import java.math.RoundingMode


class TodayDataFragment : Fragment() {

        private val TAG = "Navigation Error"

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            println("Made TDF!?")

            val appHome = HomeActivity()
            val dateToday = appHome.getOurDate()
            val timeNow = appHome.getOurTime()

            val consumpAct = ConsumableActivity()
            val wasteAct = WasteLogActivity()


            val db = FirebaseFirestore.getInstance()
            val userPath = (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
            val todayFbCollection = db.collection("users").document(userPath).collection(dateToday)
            //val dateDoc = db.collection(userPath).document(dateToday) // Doc of data for todays date
            //val travelCollection = dateDoc.collection("travel-data") //Travel data for the day

            //val totalRef = db.collection(userPath).document(dateToday).collection("total-data")
            //val travelTotalRef = totalRef.document("travel-total")
//            val travelTotalRef = db.collection(userPath).document(dateToday).collection("transportation").document(dateToday)
//            val consumpTotalRef = totalRef.document("consump-total")
//            val wasteTotalRef = totalRef.document("waste-total")
            //val db = FirebaseFirestore.getInstance()

            //val travelRef = db.collection("users").document(currentUser).collection(selectedDate).document("transportation")
            val consumpRef =  todayFbCollection.document("consumables")
            val wasteRef =  todayFbCollection.document("waste")
            val travelRef =  todayFbCollection.document("transportation")

            var waterFpTotal  = 0.0

            travelRef.get().addOnSuccessListener { result ->

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
                //var totalKm: Float = totalMiles * 1.61.toFloat()
                //totalKmVar.text = ("$totalKm Kilometers")


                //var totalCarbonFp = if(result.get("carbon_fp_sum")!= null) result.get("carbon_fp_sum") else 0f
                var totalCarbonFp = if(result.get("carb_footprint")!= null) result.get("carb_footprint") else 0f
                //totalCarbonFp = totalCarbonFp
                totalCarbonFp = totalCarbonFp.toString().toBigDecimal()
                totalCarbonFp = totalCarbonFp.setScale(2, RoundingMode.HALF_EVEN).toDouble()

                carbonFootrpintVar.text = ("$totalCarbonFp lb of C02e")

            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting travel total: ", exception)
            }

            consumpRef.get().addOnSuccessListener { result ->
                //val totalConsump = if (result.get("sum_total") != null) result.get("sum_total").toString().toFloat() else 0f
//                val waterFP = consumpAct.consumableImpact().toString().toDouble() + wasteAct.wasteImpact().toString().toDouble()
                //waterFpVar.text = ("$totalConsump lbs")
                //waterFpVar.text = ("$waterFP gals/water")

                val cowTotalValue: Float = if (result.get("cow_oz") != null) result.get("cow_oz").toString().toFloat() else 0f
                val pigTotalValue: Float = if (result.get("pig_oz") != null) result.get("pig_oz").toString().toFloat() else 0f
                val chickenTotalValue: Float = if (result.get("chicken_oz") != null) result.get("chicken_oz").toString().toFloat() else 0f
                val waterFpConsump: Float = if (result.get("water_fp_consum") != null) result.get("water_fp_consum").toString().toFloat() else 0f

                //cowTotalVar.text = cowTotalValue.toString()
                //pigTotalVar.text = pigTotalValue.toString()
                //chickenTotalVar.text = chickenTotalValue.toString()

                cowTotalVar.text = ("$cowTotalValue oz.")
                pigTotalVar.text = ("$pigTotalValue oz.")
                chickenTotalVar.text = ("$chickenTotalValue oz.")


                waterFpTotal += waterFpConsump.toString().toDouble()
                waterFpVar.text = waterFpTotal.toString() + "gallons"

            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting Consumption Total: ", exception)
            }

            wasteRef.get().addOnSuccessListener { result ->
                //var totalWaste = if (result.get("sum_total") != null) result.get("sum_total").toString().toFloat() else 0f
                //totalWasteVar.text = ("$totalWaste lbs")

                var plasticTotalValue = if (result.get("plastic_items") != null) result.get("plastic_items").toString().toFloat() else 0f
                var recycleTotalValue = if (result.get("recycled_items") != null) result.get("recycled_items").toString().toFloat() else 0f
                var trashTotalValue = if (result.get("trash_lbs") != null) result.get("trash_lbs").toString().toFloat() else 0f
                var waterFpPlastic = if (result.get("water_fp_plastic") != null) result.get("water_fp_plastic").toString().toFloat() else 0f

                //plasticsTotalVar.text = plasticTotalValue.toString()
                //recycleTotalVar.text = recycleTotalValue.toString()
                //trashTotalVar.text = trashTotalValue.toString()
                //plasticTotalValue = plasticTotalValue.toString().toBigDecimal()

                plasticsTotalVar.text = ("$plasticTotalValue units")
                recycleTotalVar.text = ("$recycleTotalValue units")
                trashTotalVar.text = ("$trashTotalValue lbs")


                waterFpTotal += waterFpPlastic.toString().toDouble()
                //waterFpVar.text = waterFpTotal.toString()
                waterFpVar.text = ("$waterFpTotal gallons")

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

            return inflater.inflate(R.layout.fragment_today, container, false)
        }

}