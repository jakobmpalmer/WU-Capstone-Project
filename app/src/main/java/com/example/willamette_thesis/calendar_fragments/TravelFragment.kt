package com.example.willamette_thesis.calendar_fragments

//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.willamette_thesis.CalendarFragment
import com.example.willamette_thesis.HomeActivity
import com.example.willamette_thesis.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_today.view.*
import java.math.RoundingMode


class TravelFragment : Fragment() {

    //private lateinit var database: DatabaseReference

    //add the tag
    val TAG: String = "ECO-FR3ndly"

//    private var ids: Array<String?>? = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000)
//    private var pdt: SimpleTimeZone = SimpleTimeZone(-8 * 60 * 60 * 1000, ids?.get(0))
//    private var calendar: Calendar = GregorianCalendar(this.pdt)

    val appHome = HomeActivity()
    val ourDate = appHome.getOurDate()
    val ourTime = appHome.getOurTime()
    val currentUser = appHome.getUserEmail()
    val calFrag = CalendarFragment()

    private val db = FirebaseFirestore.getInstance()
    private val userCol = db.collection("users").document(appHome.getUserEmail())

    var ourSelectedDay: String = ""
    private val REF_PREF_FILE = "com.calref.prefs"


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            super.onCreate(savedInstanceState)
            //setContentView(R.layout.activity_car)
            val travelView: View = inflater.inflate(
                R.layout.travel_fragment,
                container,
                false
            )
            println("Creating travel fragment.. .")

            val sharedPref = this.activity!!.getSharedPreferences(REF_PREF_FILE, Context.MODE_PRIVATE)
            ourSelectedDay = getPref(sharedPref, "ourCurrentDateStr", appHome.getOurDate())
            val travelRef = userCol.collection(ourSelectedDay).document("transportation")
            
        //Fetch Values
            travelRef.get().addOnSuccessListener { result ->

                var carTotalValue = if (result.get("car_miles") != null) result.get("car_miles").toString().toFloat() else 0f
                var busTotalValue = if (result.get("bus_miles") != null) result.get("bus_miles").toString().toFloat() else 0f
                var planeTotalValue = if (result.get("plane_miles") != null) result.get("plane_miles").toString().toFloat() else 0f
                var walkTotalValue = if (result.get("walk_miles") != null) result.get("walk_miles").toString().toFloat() else 0f

                travelView.carTotalVar.text = carTotalValue.toString()
                travelView.busTotalVar.text = busTotalValue.toString()
                travelView.planeTotalVar.text = planeTotalValue.toString()
                travelView.walkTotalVar.text = walkTotalValue.toString()

                var totalMiles = carTotalValue + busTotalValue + planeTotalValue + walkTotalValue
                travelView.totalMilesVar.text = totalMiles.toString()
                var totalKm: Float = totalMiles * 1.61.toFloat()
                //totalKmVar.text = ("$totalKm Kilometers")

                var totalCarbonFp = if(result.get("carb_footprint")!= null) result.get("carb_footprint") else 0f
                totalCarbonFp = totalCarbonFp.toString().toBigDecimal().setScale(2,RoundingMode.HALF_EVEN)


                travelView.carbonFootrpintVar.text = ("$totalCarbonFp lbs of C02e")

            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting travel total: ", exception)
            }



                return travelView
        }

        fun getPref(sharedPref: SharedPreferences, pref_to_retrieve: String, default : String) : String {
            val value_retrieved: String = sharedPref.getString(pref_to_retrieve, default)
            return value_retrieved
        }

}
