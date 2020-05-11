package com.example.willamette_thesis.calendar_fragments

/*
WaterFPFragment.ks
The purpose of this class is to create and control the water footprint fragment on the calender fragment.
The control involves creating a firebase reference, as well as a reference to the shared preferences.
This firebase reference is responsible for getting waste and consumable data to calculate the total
water footprint.
 */

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.willamette_thesis.CalendarFragment
import com.example.willamette_thesis.HomeActivity
import com.example.willamette_thesis.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.waterfootprint_fragment.*

class WaterFPFragment : Fragment() {

    val appHome = HomeActivity()
    var calFrag = CalendarFragment()
    //val selectedDay = calFrag.getSelectedDateRef()

//PREFRENCES
    var ourSelectedDay: String = ""
    //private val REF_PREF_FILE = "our-calref-prefs"
    private val REF_PREF_FILE = "com.calref.prefs"

    //private var mLastClickTime: Long = 0

    val db = FirebaseFirestore.getInstance()
    val userCol = db.collection("users").document(appHome.getUserEmail())

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            val thisView = inflater.inflate(R.layout.waterfootprint_fragment, container, false)
            val sharedPref = this.activity!!.getSharedPreferences(REF_PREF_FILE, Context.MODE_PRIVATE)
            //val sharedPref = PreferenceManager.getDefaultSharedPreferences(this.context)
            ourSelectedDay = getPref(sharedPref, "ourCurrentDateStr", appHome.getOurDate())
            println("ourSelectedDateHERE!@!!: $ourSelectedDay")

            val consumpRef = userCol.collection(ourSelectedDay).document("consumables")
            val wasteRef = userCol.collection(ourSelectedDay).document("waste")
            //println("ourday: $selectedDay")


            //if (SystemClock.elapsedRealtime() - mLastClickTime < 3000){

            wasteRef.get().addOnSuccessListener { result ->

                var plasticTotalValue = if (result.get("plastic_items") != null) result.get("plastic_items").toString().toFloat() else 0f
                var recycleTotalValue = if (result.get("recycled_items") != null) result.get("recycled_items").toString().toFloat() else 0f
                var waterFpValue = if (result.get("water_fp_plastic") != null) result.get("water_fp_plastic").toString().toFloat() else 0f

                plasticsTotalVar.text = if(plasticTotalValue.toString() != null) plasticTotalValue.toString() else "0 lbs"
                recycleTotalVar.text = if(recycleTotalValue.toString() != null) recycleTotalValue.toString() else "0 lbs"

                var tempWaterFP = waterFpVar.text.toString().toDouble()
                tempWaterFP += waterFpValue
                waterFpVar.text = tempWaterFP.toString()
                //ourWaterFP += waterFpValue

            }.addOnFailureListener { exception ->
                println("Couldent access todayWasteDoc")
            }

            //}
            //mLastClickTime = SystemClock.elapsedRealtime()

            consumpRef.get().addOnSuccessListener { result ->

                var chickenVal = if (result.get("chicken_oz") != null) result.get("chicken_oz").toString().toFloat() else 0f
                var cowVal = if (result.get("cow_oz") != null) result.get("cow_oz").toString().toFloat() else 0f
                var pigVal = if (result.get("pig_oz") != null) result.get("pig_oz").toString().toFloat() else 0f
                var waterFpVal = if (result.get("water_fp_consum") != null) result.get("water_fp_consum").toString().toFloat() else 0f

                chickenTotalVar.text = if(chickenVal.toString() != null) chickenVal.toString() else "0 oz"
                cowTotalVar.text = if(cowVal.toString() != null) cowVal.toString() else "0 oz"
                pigTotalVar.text = if(pigVal.toString() != null) pigVal.toString() else "0 oz"


                var tempWaterFP = waterFpVar.text.toString().toDouble()
                tempWaterFP += waterFpVal
                waterFpVar.text = tempWaterFP.toString()

            }.addOnFailureListener { exception ->
                println("Couldent access comsumpRef")
            }

            return thisView
        }


    fun getPref(sharedPref: SharedPreferences, pref_to_retrieve: String, default : String) : String {
        val value_retrieved: String = sharedPref.getString(pref_to_retrieve, default)
        return value_retrieved
    }


}