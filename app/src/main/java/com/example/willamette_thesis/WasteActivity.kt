package com.example.willamette_thesis


import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_waste.*
import java.util.*


class WasteActivity : AppCompatActivity() {



    private val db = FirebaseFirestore.getInstance()
    //add the tag
    val TAG: String = "ECO-FR3ndly"


//Calendar
    // create a Pacific Standard Time time zone
    // get the supported ids for GMT-08:00 (Pacific Standard Time)
    private var ids: Array<String?>? = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000)
    private var pdt: SimpleTimeZone = SimpleTimeZone(-8 * 60 * 60 * 1000, ids?.get(0))
    private var calendar: Calendar = GregorianCalendar(this.pdt)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waste)
        //val binding = R.layout.ActivityWasteBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        println("This Date= ${getOurDate()}")
        println("Our Time: ${getOurTime()}")

        val submitWasteBtn = findViewById<Button>(R.id.submitWaste_Button)
        submitWasteBtn.setOnClickListener {
            //var data = getData()
            Toast.makeText(this@WasteActivity, "Waste info for the day has been recorded", Toast.LENGTH_SHORT).show()
            storeData(getOurDate(), getOurTime())
        }
    }

    private fun storeData(someDate: String, someTime: String) {
        // Create a new user with a first and last name
        val plasticData = if (plastic_text.text.isNotEmpty()) plastic_text.text.toString().toInt() else 0
        val recycleData = if (recycle_text.text.isNotEmpty()) recycle_text.text.toString().toInt() else 0
        val trashData = if (trash_text.text.isNotEmpty()) trash_text.text.toString().toInt() else 0


        val data = hashMapOf(
//            "date" to someDate,
//            "time" to someTime,
            "plastic_data" to plasticData,
            "recycle_data" to recycleData,
            "trash_data" to trashData
        )
// Add a new document with a generated ID
        val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")


        val totalRef = db.collection(userPath).document(someDate).collection("total-data")
        val wasteTotalRef = totalRef.document("waste-total")


        wasteTotalRef.get().addOnSuccessListener { result ->
            var oldPlasticTotal = result?.get("plastic_total").toString().toFloatOrNull()
            var oldRecycleTotal = result?.get("recycle_total").toString().toFloatOrNull()
            var oldTrashTotal = result?.get("trash_total").toString().toFloatOrNull()


            var newPlasticTotal = plasticData + if (oldPlasticTotal != null) oldPlasticTotal else 0f
            var newRecycleTotal = recycleData + if (oldRecycleTotal != null) oldRecycleTotal else 0f
            var newTrashTotal = trashData + if (oldTrashTotal != null) oldTrashTotal else 0f
            var sumTotal = newPlasticTotal + newRecycleTotal + newTrashTotal

            val totalData = hashMapOf(
                "plastic_total" to newPlasticTotal,
                "recycle_total" to newRecycleTotal,
                "trash_total" to newTrashTotal,
                "sum_total" to sumTotal
            )

            //totalRef.update(totalData as Map<String, Float>)
            println("Saving total data:: $totalData")
            wasteTotalRef.set(totalData)
        }
//        db.collection(userPath)
//            .add(data)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }
        //db.collection(userPath).document("/waste-data").set(data)
        db.collection(userPath).document(someDate).collection("waste-data").document(someTime).set(data)
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

    fun wasteImpact() : Pair<Double, Double>{
        val plastic_data = if (plastic_text.text.isNotEmpty()) plastic_text.text.toString().toInt() else 0
        val recycle_data = if (recycle_text.text.isNotEmpty()) recycle_text.text.toString().toInt() else 0
        val trash_data = if (trash_text.text.isNotEmpty()) trash_text.text.toString().toInt() else 0

        val plasticImpact = ((plastic_data - recycle_data)* 20.0 * 2.0)/128
        // Idicates gallons of water wasted. Assumes average 20oz water bottle size. Multiplying by two as producing plastic
        // bottle takes about double they size in water. We divide by 128 as there are 128 oz in a gallon of water
        val trashImpact = trash_data * 22.0
        // indicates weight in lbs of trash produced

        val impact_returned = Pair(plasticImpact,trashImpact)
        return impact_returned
    }


}
