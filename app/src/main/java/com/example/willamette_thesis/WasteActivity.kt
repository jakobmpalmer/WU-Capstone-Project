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
        val plastic_data = if (plastic_text.text.isNotEmpty()) plastic_text.text.toString().toInt() else 0
        val recycle_data = if (recycle_text.text.isNotEmpty()) recycle_text.text.toString().toInt() else 0
        val trash_data = if (trash_text.text.isNotEmpty()) trash_text.text.toString().toInt() else 0


        val data = hashMapOf(
//            "date" to someDate,
//            "time" to someTime,
            "plastic_data" to plastic_data,
            "recycle_data" to recycle_data,
            "trash_data" to trash_data
        )
// Add a new document with a generated ID
        val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
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


}
