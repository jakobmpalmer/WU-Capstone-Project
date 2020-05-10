package com.example.willamette_thesis.calendar_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.willamette_thesis.CalendarFragment
import com.example.willamette_thesis.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.trash_fragment.*

class WasteFragment : Fragment() {


    var calFrag = CalendarFragment()
    val selectedDay = calFrag.getSelectedDateRef()

    val db = FirebaseFirestore.getInstance()
    val wasteRef = selectedDay.document("waste")



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val wasteView = inflater.inflate(R.layout.trash_fragment, container, false)


        wasteRef.get().addOnSuccessListener { result ->

            var trashValue = if (result.get("trash_lbs") != null) result.get("trash_lbs").toString().toFloat() else 0f

            trashTotalVar.text = trashValue.toString()

        }.addOnFailureListener { exception ->
            println("Couldent access todayWasteDoc")
        }

        return wasteView
    }
}