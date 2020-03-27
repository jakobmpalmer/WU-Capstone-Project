package com.example.willamette_thesis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WasteDataFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

    //    companion object {
    //        fun newInstance(): WasteDataFragment = WasteDataFragment()
    //    }
        val appHome = HomeActivity()
        val dateToday = appHome.getOurDate()
        val timeNow = appHome.getOurTime()

        val db = FirebaseFirestore.getInstance()
        val userPath = (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
        val dateRef = db.collection(userPath).document(dateToday).collection(timeNow)
        val wasteRef = dateRef.document("waste-data")





        return inflater.inflate(R.layout.fragment_waste, container, false)
    }
}