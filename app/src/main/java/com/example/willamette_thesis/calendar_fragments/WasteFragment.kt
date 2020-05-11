package com.example.willamette_thesis.calendar_fragments

/*
WasteFragment.ks
This class is responsible for creating the Waste fragment on the calendar activity. It gets waste data from firebase, and
is responsible for displaying the total trash in pounds.
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
import kotlinx.android.synthetic.main.trash_fragment.*

class WasteFragment : Fragment() {


    val appHome = HomeActivity()
    //var calFrag = CalendarFragment()
    //val selectedDay = calFrag.getSelectedDateRef()

    val db = FirebaseFirestore.getInstance()
    private val userCol = db.collection("users").document(appHome.getUserEmail())

    var ourSelectedDay: String = ""
    private val REF_PREF_FILE = "com.calref.prefs"
    //val wasteRef = selectedDay.document("waste")





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val wasteView = inflater.inflate(R.layout.trash_fragment, container, false)


        val sharedPref = this.activity!!.getSharedPreferences(REF_PREF_FILE, Context.MODE_PRIVATE)
        ourSelectedDay = getPref(sharedPref, "ourCurrentDateStr", appHome.getOurDate())
        val wasteRef = userCol.collection(ourSelectedDay).document("waste")

        wasteRef.get().addOnSuccessListener { result ->

            var trashValue = if (result.get("trash_lbs") != null) result.get("trash_lbs").toString().toFloat() else 0f

            trashTotalVar.text = trashValue.toString()

        }.addOnFailureListener { exception ->
            println("Couldent access todayWasteDoc")
        }

        return wasteView
    }

    fun getPref(sharedPref: SharedPreferences, pref_to_retrieve: String, default : String) : String {
        val value_retrieved: String = sharedPref.getString(pref_to_retrieve, default)
        return value_retrieved
    }

}