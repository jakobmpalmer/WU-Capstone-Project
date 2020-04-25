package com.example.willamette_thesis

//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_car.*
import kotlinx.android.synthetic.main.profile_activity.*
import java.util.*


class CarActivity : AppCompatActivity() {

    //private lateinit var database: DatabaseReference
    private val db = FirebaseFirestore.getInstance()
    //add the tag
    val TAG: String = "ECO-FR3ndly"


    private var ids: Array<String?>? = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000)
    private var pdt: SimpleTimeZone = SimpleTimeZone(-8 * 60 * 60 * 1000, ids?.get(0))
    private var calendar: Calendar = GregorianCalendar(this.pdt)

    //val appProfile = ProfileActivity()
    //val mpg = appProfile.getMileageSelected()  // gets mileage selected
    //val fuel_rate_selected = appProfile.getFuelSelected()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        val submitButton = findViewById<Button>(R.id.button1)

        submitButton.setOnClickListener{ it: View? ->
            //var input = car_input.toString().toDouble()
            if(car_text.text.isEmpty()){
                println("21Empty")
            } else if(car_text.text.isBlank()){
                println("21Blank")
            } else if(car_text.text.isNullOrBlank()){
            println("21Null of Blank")
            } else {
                println("None21")
            }

            Toast.makeText(this@CarActivity, "Transportation info for the day has been recorded", LENGTH_SHORT).show()

            //var ourData = getTranspoData()
            //storeData("DATE", ourData)
            //storeData("DATE", "[0,0,125]")
            //storeData(LocalDateTime.now().toString(), getTranspoData()) //REQUIRES API MIN 26 --Current 24
            storeData(getOurDate(), getOurTime())
        }

        //database = FirebaseDatabase.getInstance().reference

    }


// REALTIME METHOD OF STORAGE
//    private fun storeData(someDate: String){ // Change someData to an intarray?
//
//        val ref = FirebaseDatabase.getInstance().getReference("transpo-data") // Refrence to database
//        println("Got REF")
//
//        val car_data = if (car_text.text != null) car_text.text.toString().toInt() else 0
//        val bus_data = if (bus_text.text != null) bus_text.text.toString().toInt() else 0
//        val plane_data = if (plane_text.text != null) plane_text.text.toString().toInt() else 0
//        val walk_data = if (walk_text.text != null) walk_text.text.toString().toInt() else 0
//        //database.child("users").child(userId).setValue(user)
//
//        //database.child("server/saving-data/transpo-data").child()
//        val dataId = ref!!.push().key!!
//        println("Got dataID")
//        val tData = TranspoData(someDate, car_data, bus_data, plane_data, walk_data)
//        println("Got tData")
//        ref.child(dataId).setValue(tData)
//        println("Done")
//    }

/* Method used to write user data to the cloud firestore
*  2 / 24 / 2020
*
* */
    private fun storeData(ourDate: String, ourTime: String) {

        //val updates = HashMap<String, Any>()


        val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
        val travelRef = db.collection(userPath).document(ourDate).collection("travel-data")
        val totalRef = db.collection(userPath).document(ourDate).collection("total-data")
        val travelTotalRef = totalRef.document("travel-total")
        //updates[totalRef + '/car_data'] =

        val carData = if (car_text.text.isNotEmpty()) car_text.text.toString().toFloat() else 0f
        val busData = if (bus_text.text.isNotEmpty()) bus_text.text.toString().toFloat() else 0f
        val planeData = if (plane_text.text.isNotEmpty()) plane_text.text.toString().toFloat() else 0f
        val walkData = if (walk_text.text.isNotEmpty()) walk_text.text.toString().toFloat() else 0f

        println("ourdata= $carData, $busData, $planeData, $walkData")
        println("our CarbonFootprint: ${calcImpact()}")

        val data = hashMapOf(

            "car_data" to carData,
            "bus_data" to busData,
            "plane_data" to planeData,
            "walk_data" to walkData,
            "carbon_footprint" to calcImpact()
        )


        travelTotalRef.get().addOnSuccessListener { result ->
            var oldBusTotal = result?.get("bus_total").toString().toFloatOrNull()
            var oldCarTotal = result?.get("car_total").toString().toFloatOrNull()
            var oldPlaneTotal = result?.get("plane_total").toString().toFloatOrNull()
            var oldWalkTotal = result?.get("walk_total").toString().toFloatOrNull()
            var oldCarbonFpTotal = result?.get("carbon_fp_sum").toString().toFloatOrNull()


            var newCarTotal = carData + if (oldCarTotal != null) oldCarTotal else 0f
            var newBusTotal = busData + if (oldBusTotal != null) oldBusTotal else 0f
            var newPlaneTotal = planeData + if (oldPlaneTotal != null) oldPlaneTotal else 0f
            var newWalkTotal = walkData + if (oldWalkTotal != null) oldWalkTotal else 0f
            var newCarbonFpTotal = calcImpact() + if (oldCarbonFpTotal != null) oldCarbonFpTotal else 0f
                                    // is using correct fuel rate and mpg
            var sumTotal = newCarTotal + newBusTotal + newPlaneTotal + newWalkTotal

            val totalData = hashMapOf(
                "car_total" to newCarTotal,
                "bus_total" to newBusTotal,
                "plane_total" to newPlaneTotal,
                "walk_total" to newWalkTotal,
                "sum_total" to sumTotal,
                "carbon_fp_sum" to newCarbonFpTotal
            )

            //totalRef.update(totalData as Map<String, Float>)
            println("Saving total data:: $totalData")
            travelTotalRef.set(totalData)
        } // travelTotal.get
//
//        updates["/car_data"] = carTotal
// Add a new document with a generated ID

        //db.collection("/travel-data")
//        db.collection(userPath)
//            .add(data)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }

        //db.collection(userPath).document("travel-data").set(data)
        db.collection(userPath).document(ourDate).collection("travel-data").document(ourTime).set(data)
        //totalRef.set(updatedTotal)
    }


    fun getOurDate() : String{
        var ourYear = calendar.get(Calendar.YEAR)
        var ourMonth = calendar.get(Calendar.MONTH)
        var ourDay = calendar.get(Calendar.DAY_OF_MONTH)

        return ("$ourYear, $ourMonth, $ourDay")
    }

    private fun getOurTime() : String{
        var ourHour = calendar.get(Calendar.HOUR_OF_DAY)
        var ourMin = calendar.get(Calendar.MINUTE)
        var ourSec = calendar.get(Calendar.SECOND)
        var ourMilisec = calendar.get(Calendar.MILLISECOND)

        return ("$ourHour, $ourMin, $ourSec, $ourMilisec")
    }


    fun calcImpact(fuel_rate : Double = 21.25, miles_per_gal : Double = 19.73): Double{
        // fuel rate is the standard rate of CO2 commissions for fuel. 21.25 is the average of petrol (20lbs)
        // and diesel (22.5). If we get the type of fuel the user user we can make if more accurate.
        // miles_per_gal is how many miles a car can go with a gallon of fuel. Depends on brand and make of car.
        // we are using an average as default (21.3), but can make it more precise if need be.


        val carData = if (car_text.text.isNotEmpty()) car_text.text.toString().toInt() else 0
        val busData = if (bus_text.text.isNotEmpty()) bus_text.text.toString().toInt() else 0
        val planeData = if (plane_text.text.isNotEmpty()) plane_text.text.toString().toInt() else 0
        val walkData = if (walk_text.text.isNotEmpty()) walk_text.text.toString().toInt() else 0

        val flight_hours = planeData/565
        // 565 is the average num of miles in an hour
        val flight_type = if (flight_hours >= 4) 4400 else 1100


        val carEmission = (carData-walkData/miles_per_gal) * fuel_rate
        // taking walkData from carData, as we make the assumption that walking is a replacement for using a car
        val busEmission = ((busData/12.52) * 22.5) / 60
        // Assumption that buses use diesel, hence the fuel rate used is 22.5. We divide by 60 as on average
        // a bus has an av seating capacity of 40-80, so we average that out
        // 12.52 is used as miles per gal, as we assume that it is a Passenger Van
        val planeEmission = planeData * flight_type * 21.25
        // 21.25 is average on fuel types, as users are unlikely to have this info

        return (carEmission + busEmission + planeEmission)

    }


}
