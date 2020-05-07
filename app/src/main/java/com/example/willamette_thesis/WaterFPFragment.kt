package com.example.willamette_thesis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.waterfootprint_fragment.*

class WaterFPFragment : Fragment() {



    var calFrag = CalendarFragment()
    //var chosenDate = calFrag.getSelectedDate()
    //var chosenDateRef = calFrag.getSelectedDateRef()
    val selectedDay = calFrag.getSelectedDateRef()

    val db = FirebaseFirestore.getInstance()

    //val travelRef = db.collection("users").document(currentUser).collection(selectedDate).document("transportation")
    val consumpRef = selectedDay.document("consumables")
    val wasteRef = selectedDay.document("waste")


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            val thisView = inflater.inflate(R.layout.waterfootprint_fragment, container, false)
//
//            var plasticTotalValue = 0.0f
//            var recycleTotalValue = 0.0f
//            var trashTotalValue = 0.0f
            //println("current dateDoc ${dateCol}")

            var ourWaterFP = 0.0f
            println("1Our waterFPP = $ourWaterFP")

            wasteRef.get().addOnSuccessListener { result ->
                var totalWaste = if (result.get("sum_total") != null) result.get("sum_total").toString().toFloat() else 0f
                //totalWasteVar.text = ("$totalWaste lbs")

                var plasticTotalValue = if (result.get("plastic_items") != null) result.get("plastic_items").toString().toFloat() else 0f
                var recycleTotalValue = if (result.get("recycled_items") != null) result.get("recycled_items").toString().toFloat() else 0f
                //var trashTotalValue = if (result.get("trash_lbs") != null) result.get("trash_lbs").toString().toFloat() else 0f
                var waterFpValue = if (result.get("water_fp_plastic") != null) result.get("water_fp_plastic").toString().toFloat() else 0f

                plasticsTotalVar.text = plasticTotalValue.toString()
                recycleTotalVar.text = recycleTotalValue.toString()
                //trashTotalVar.text = trashTotalValue.toString()

                println("WaterFP from waste $waterFpValue")
                var tempWaterFP = waterFpVar.text.toString().toDouble()
                tempWaterFP += waterFpValue
                waterFpVar.text = tempWaterFP.toString()
                //ourWaterFP += waterFpValue
                println("2Our waterFPP = $ourWaterFP")

            }.addOnFailureListener { exception ->
                println("Couldent access todayWasteDoc")
            }

            consumpRef.get().addOnSuccessListener { result ->

                var chickenVal = if (result.get("chicken_oz") != null) result.get("chicken_oz").toString().toFloat() else 0f
                var cowVal = if (result.get("cow_oz") != null) result.get("cow_oz").toString().toFloat() else 0f
                var pigVal = if (result.get("pig_oz") != null) result.get("pig_oz").toString().toFloat() else 0f
                var waterFpVal = if (result.get("water_fp_consum") != null) result.get("water_fp_consum").toString().toFloat() else 0f

                chickenTotalVar.text = chickenVal.toString()
                cowTotalVar.text = cowVal.toString()
                pigTotalVar.text = pigVal.toString()


                println("WaterFP from comsump $waterFpVal")
                var tempWaterFP = waterFpVar.text.toString().toDouble()
                tempWaterFP += waterFpVal
                waterFpVar.text = tempWaterFP.toString()
                //ourWaterFP += waterFpVal
                println("3Our waterFPP = $ourWaterFP")

            }.addOnFailureListener { exception ->
                println("Couldent access comsumpRef")
            }

            //waterFpVar.text = ourWaterFP.toString()
            println("4Our waterFPP = $ourWaterFP")




            return thisView
        }


}