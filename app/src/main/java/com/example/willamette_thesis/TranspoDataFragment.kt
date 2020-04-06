package com.example.willamette_thesis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_transpo.*

class TranspoDataFragment : Fragment() {

        private val TAG = "Navigation Error"

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            //homeViewModel =
            //ViewModelProviders.of(this).get(HomeViewModel::class.java)
            //val root = inflater.inflate(R.layout.fragment_transpo, container, false)

            //val textView: TextView = root.findViewById(R.id.text_home)
            //homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
            //})
            //return root
            val db = FirebaseFirestore.getInstance()
            val userPath = (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
            val travelRef = db.collection(userPath).document("travel-data")
            //val dateRef = db.collection(userPath).document(requestedDate).collection(requestedTime).document("travel-data")
            // ABOVE to be implemented with date select


            travelRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        var carData = (document.data?.get("car_data")?.toString() ?: "NULL_VALUE")
                        var busData = (document.data?.get("bus_data")?.toString() ?: "NULL_VALUE")
                        var airData = (document.data?.get("plane_data")?.toString() ?: "NULL_VALUE")
                        var walkData = (document.data?.get("walk_data")?.toString() ?: "NULL_VALUE")

                        var carTotal = carData.toFloat()
                        var busTotal = busData.toFloat()
                        var airTotal = airData.toFloat()
                        var walkTotal = walkData.toFloat()

                        var totalMiles = carTotal + busTotal + airTotal + walkTotal

                        totalMilesText.text = totalMiles.toString() + "miles"
                        carbonFootrpintText.text = calculateCarbonFPCar(totalMiles).toString() + " C02e"
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }

            return inflater.inflate(R.layout.fragment_transpo, container, false)
        }

    /*
    Vehicle : distance (km/yr) /*EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Bus : distance (km/yr) * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Metro: distance (km/yr) * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Taxi: distance (km/yr) * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Rail: distance (km/yr) * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Flying : distance (km/yr)* 1.09 * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    */*/

    private fun calculateCarbonFPCar(miles: Float) : Float{
        return miles * 8.31f
    }




}