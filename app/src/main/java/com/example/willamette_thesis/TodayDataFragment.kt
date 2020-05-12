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

    private val appHome = HomeActivity()
    private val dateToday = appHome.getOurDate()

    private val db = FirebaseFirestore.getInstance()
    private val userPath = (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
    private val todayFbCollection = db.collection("users").document(userPath).collection(dateToday)

    private val consumpRef =  todayFbCollection.document("consumables")
    private val wasteRef =  todayFbCollection.document("waste")
    private val travelRef =  todayFbCollection.document("transportation")



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

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

                var totalMiles = carTotalValue + busTotalValue + planeTotalValue + walkTotalValue
                totalMilesVar.text = totalMiles.toString()

                var totalCarbonFp = if(result.get("carb_footprint")!= null) result.get("carb_footprint") else 0f
                totalCarbonFp = totalCarbonFp.toString().toBigDecimal()
                totalCarbonFp = totalCarbonFp.setScale(2, RoundingMode.HALF_EVEN).toDouble()

                carbonFootrpintVar.text = ("$totalCarbonFp lb of C02e")

            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting travel total: ", exception)
            }

            consumpRef.get().addOnSuccessListener { result ->

                val cowTotalValue: Float = if (result.get("cow_oz") != null) result.get("cow_oz").toString().toFloat() else 0f
                val pigTotalValue: Float = if (result.get("pig_oz") != null) result.get("pig_oz").toString().toFloat() else 0f
                val chickenTotalValue: Float = if (result.get("chicken_oz") != null) result.get("chicken_oz").toString().toFloat() else 0f
                val waterFpConsump: Float = if (result.get("water_fp_consum") != null) result.get("water_fp_consum").toString().toFloat() else 0f

                cowTotalVar.text = ("$cowTotalValue oz.")
                pigTotalVar.text = ("$pigTotalValue oz.")
                chickenTotalVar.text = ("$chickenTotalValue oz.")


                waterFpTotal += waterFpConsump.toString().toDouble()
                waterFpVar.text = waterFpTotal.toString() + " gallons"

            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting Consumption Total: ", exception)
            }

            wasteRef.get().addOnSuccessListener { result ->

                var plasticTotalValue = if (result.get("plastic_items") != null) result.get("plastic_items").toString().toFloat() else 0f
                var recycleTotalValue = if (result.get("recycled_items") != null) result.get("recycled_items").toString().toFloat() else 0f
                var trashTotalValue = if (result.get("trash_lbs") != null) result.get("trash_lbs").toString().toFloat() else 0f
                var waterFpPlastic = if (result.get("water_fp_plastic") != null) result.get("water_fp_plastic").toString().toFloat() else 0f

                plasticsTotalVar.text = ("$plasticTotalValue units")
                recycleTotalVar.text = ("$recycleTotalValue units")
                trashTotalVar.text = ("$trashTotalValue lbs")


                waterFpTotal += waterFpPlastic.toString().toDouble()
                //waterFpVar.text = waterFpTotal.toString()
                waterFpVar.text = ("$waterFpTotal gallons")

            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting Waste Total: ", exception)
            }

            return inflater.inflate(R.layout.fragment_today, container, false)
        }

}