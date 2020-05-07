package com.example.willamette_thesis

//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_car.*
import kotlinx.android.synthetic.main.activity_car.view.*
import kotlinx.android.synthetic.main.fragment_today.view.*
import java.util.*


class TravelFragment : Fragment() {

    //private lateinit var database: DatabaseReference
    private val db = FirebaseFirestore.getInstance()
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




        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            super.onCreate(savedInstanceState)
            //setContentView(R.layout.activity_car)
            val travelView: View = inflater.inflate(
                R.layout.travel_fragment,
                container,
                false
            )

            var selectedDate = calFrag.getSelectedDate()
            val selectedDay = calFrag.getSelectedDateRef()
            //val travelRef = db.collection("users").document(currentUser).collection(selectedDate).document("transportation")
            val travelRef = selectedDay.document("transportation")
            println("travelRef gotten!")


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

                //var totalMiles = if (result.get("sum_miles") != null) result.get("sum_total").toString().toFloat() else 0f
                //totalMilesVar.text = if(totalMiles != null) ("$totalMiles miles") else 0.toString()
                var totalMiles = carTotalValue + busTotalValue + planeTotalValue + walkTotalValue
                travelView.totalMilesVar.text = totalMiles.toString()
                var totalKm: Float = totalMiles * 1.61.toFloat()
                //totalKmVar.text = ("$totalKm Kilometers")


                //var totalCarbonFp = if(result.get("carbon_fp_sum")!= null) result.get("carbon_fp_sum") else 0f
                var totalCarbonFp = if(result.get("carb_footprint")!= null) result.get("carb_footprint") else 0f
                totalCarbonFp = totalCarbonFp
                travelView.carbonFootrpintVar.text = ("$totalCarbonFp C02e")

            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting travel total: ", exception)
            }



                return travelView
        }



}
