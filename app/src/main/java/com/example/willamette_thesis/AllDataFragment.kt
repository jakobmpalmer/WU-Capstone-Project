package com.example.willamette_thesis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_all_data.*

class AllDataFragment : Fragment() {

    private val TAG = "Navigation Error"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val db = FirebaseFirestore.getInstance()
        val userPath = (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
        val travelRef = db.collection(userPath).document("travel-data")
        val wasteRef = db.collection(userPath).document("waste-data")
        val consumpRef = db.collection(userPath).document("consumable-data")

        travelRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    gasTotalTV.text = (document.data?.get("bus_data")?.toString() ?: "NULL_VALUE") + " miles"
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        wasteRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    wasteTotalTV.text = ((document.data?.get("plastic_data")?.toString() ?: "NULL_VALUE") + " plastic")

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        consumpRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    waterTotalTV.text = ((document.data?.get("water_data")?.toString() ?: "NULL_VALUE") + " water")
                    waterWasteTV.text = calcWaterWaste(document.data?.get("water_data")?.toString() ?: "NULL_VALUE").toString() + " li/yr"
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        return inflater.inflate(R.layout.fragment_all_data, container, false)

    } // On Create

    /* Function used to calculate the water emissions based off the given usage.
    Water : use (litres/day) * 365 * EF (kg CO2e/kWh) = emissions (kg CO2e/yr) */
    private fun calcWaterWaste(waterUse : String) : Float {
        var useFloat = 0f
        useFloat = if (waterUse != "NULL_VALUE") waterUse.toFloat() else 0f
        return useFloat * 365
    }

}