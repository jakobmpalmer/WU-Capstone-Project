package com.example.willamette_thesis

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

//import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_consumable.*
import java.util.*

//import kotlinx.android.synthetic.main.content_main.*


class ConsumableActivity : AppCompatActivity() {

    private var waterCount = 0
    private val db = FirebaseFirestore.getInstance()

    object OurVariables {
        var waterTotal = 0
        var gasTotal = 0
        var ml: Double = 0.0
    }

    private var ids: Array<String?>? = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000)
    private var pdt: SimpleTimeZone = SimpleTimeZone(-8 * 60 * 60 * 1000, ids?.get(0))
    private var calendar: Calendar = GregorianCalendar(pdt)


    override fun onCreate(savedInstanceState: Bundle?) {
        println("CREATED")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumable)
        //setSupportActionBar(toolbar)

        //dataDisplayTable.visibility = View.INVISIBLE

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Table Still There?", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//            fabAction()
//        }

        submitConsum_Button.setOnClickListener{ it: View? ->
            //var input = car_input.toString().toDouble()
            Toast.makeText(this@ConsumableActivity, "Consumable info for the day has been recorded", Toast.LENGTH_SHORT).show()
            storeData(getOurDate(), getOurTime())
        }

    } //Oncreate



    private fun storeData(ourDate: String, ourTime: String) {
        // Create a new user with a first and last name
        val water_data = if (water_text.text.isNotEmpty()) water_text.text.toString().toInt() else 0
        val food_data = if (food_text.text.isNotEmpty()) food_text.text.toString().toInt() else 0
        //val trash_data = if (trash_text.text != null) trash_text.text.toString().toInt() else 0


        val data = hashMapOf(
            "water_data" to water_data,
            "food_data" to food_data
        )

// Add a new document with a generated ID
        val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
        //db.collection(userPath).document("/consumable-data").set(data)
        db.collection(userPath).document(ourDate).collection("consumable-data").document(ourTime).set(data)
    }

//
//    private fun fabAction(){
//        val ddt = findViewById<TableLayout>(R.id.dataDisplayTable)
//            if(ddt.isInvisible){
//                println("- - ddt is invisible - -")
//                ddt.visibility = View.VISIBLE
//            } else{
//                println("- - ddt is visible - -")
//                ddt.visibility = View.INVISIBLE
//            }
//    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

/*Add Water Function
* Currently uses to many variables
* -Figure out whats going on while directly refrencing
* textview on WaterText line (81 currently)*/
//    private fun addWater(): String {
//        val waterText = findViewById<TextView>(R.id.waterCountDisp)
//        val waterStr: String = waterText.text.toString()
//        var currentTotal: Int = waterTotalTV.text.toString().split(" ").first().toInt()
//
//        var count = waterStr.toInt()
//        var ourCount = count + 1
//        waterCount = ourCount
//        waterCountDisp.text = "$waterCount"
//
//        var ourSpinner = waterSpinner.selectedItem.toString()
//        println("ourSpinnerr: $ourSpinner")
//        var ourSpinnerValue = ourSpinner.split(" ").first().toInt()
//        println("ourSpinnerValue: $ourSpinnerValue")
//        var waterTotal = currentTotal + ourSpinnerValue
//
//        mlTotalTV.text = calcML(waterTotal.toDouble()).toString() + " ml"
//
//        OurVariables.waterTotal = waterTotal
//        return waterTotal.toString() + " oz"
//    }
//
//
//    private fun addGas(): String {
//        //val gasText = findViewById<TextView>(R.id.gasCountDisplay)
//        //val gasStr: String = this.gasCountDisplay.toString()
//        var currentTotal = gasTotalTV.text.toString().toInt()
//
//        if(mileageInput.getText().toString() == ("")) {
//            println("\n\tMileageStr == \"\"!\n")
//            return "0"
//        }
//
//        //val mileageText = findViewById<EditText>(R.id.mileageInput)
//        val mileageStr: String = mileageInput.getText().toString()
//        val mileageInput = mileageStr.toInt()
//        var gasCount = currentTotal + mileageInput
//
//        OurVariables.gasTotal = gasCount
//        return gasCount.toString()
//    }
//
//    private fun calcML(ounces: Double): Double {
//        var mlVolValue: Double = 29.574
//        var ourML = mlVolValue * ounces
//
//        var finalML:Double = String.format("%.3f", ourML).toDouble()
//
//        OurVariables.ml = finalML
//        return finalML
//    }

    //Firebase
//    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
//    var ref: DatabaseReference = database.getReference("server/saving-data/fireblog")
    //*-*-*

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
