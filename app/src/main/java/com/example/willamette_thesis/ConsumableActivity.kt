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


    val appHome = HomeActivity()
    var ourDate = appHome.getOurDate()
    var ourTime = appHome.getOurTime()

    override fun onCreate(savedInstanceState: Bundle?) {
        println("CREATED")
        super.onCreate(savedInstanceState)
        println("ids= $ids, pdt=$pdt, calendar=$calendar")
        println("appHome= $appHome, ourTime=$ourTime, ourDate=$ourDate")
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
            //storeData(getOurDate(), getOurTime())
            storeData(ourDate, ourTime)
        }

    } //Oncreate



    private fun storeData(theDate: String, theTime: String) {
        val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
        val totalRef = db.collection(userPath).document(ourDate).collection("total-data")
        val consumpTotalRef = totalRef.document("consump-total")

        // Create a new user with a first and last name
        val cowData = if (cow_text.text.isNotEmpty()) cow_text.text.toString().toInt() else 0
        val chickenData = if (chicken_text.text.isNotEmpty()) chicken_text.text.toString().toInt() else 0
        val pigData = if (pig_text.text.isNotEmpty()) pig_text.text.toString().toInt() else 0


        val data = hashMapOf(
            "cow_data" to cowData,
            "chicken_data" to chickenData,
            "pig_data" to pigData
        )
        println("Storing:: CowData: $cowData, ChickenData: $chickenData, PigData: $pigData")
        println("Storing under Date: $theDate and Time: $theTime")
        println("CurrentUser: ${FirebaseAuth.getInstance().currentUser?.email}")



        consumpTotalRef.get().addOnSuccessListener { result ->
            var oldCowTotal = result?.get("cow_total").toString().toFloatOrNull()
            var oldPigTotal = result?.get("pig_total").toString().toFloatOrNull()
            var oldChickenTotal = result?.get("chicken_total").toString().toFloatOrNull()


            var newCowTotal = cowData + if (oldCowTotal != null) oldCowTotal else 0f
            var newPigTotal = pigData + if (oldPigTotal != null) oldPigTotal else 0f
            var newChickenTotal = chickenData + if (oldChickenTotal != null) oldChickenTotal else 0f
            var sumTotal = newCowTotal + newPigTotal + newChickenTotal

            val totalData = hashMapOf(
                "cow_total" to newCowTotal,
                "pig_total" to newPigTotal,
                "chicken_total" to newChickenTotal,
                "sum_total" to sumTotal
            )

            //totalRef.update(totalData as Map<String, Float>)
            println("Saving total data:: $totalData")
            consumpTotalRef.set(totalData)
        } // travelTotal.get


// Add a new document with a generated ID
        //val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")
        //db.collection(userPath).document("/consumable-data").set(data)
        db.collection(userPath).document(theDate).collection("consumable-data").document(theTime).set(data)
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

//    fun getOurDate() : String{
//        var ourYear = calendar.get(Calendar.YEAR)
//        var ourMonth = calendar.get(Calendar.MONTH)
//        var ourDay = calendar.get(Calendar.DAY_OF_MONTH)
//
//        return ("$ourYear, $ourMonth, $ourDay")
//    }
//
//    private fun getOurTime() : String{
//        var ourHour = calendar.get(Calendar.HOUR_OF_DAY)
//        var ourMin = calendar.get(Calendar.MINUTE)
//        var ourSec = calendar.get(Calendar.SECOND)
//        var ourMilisec = calendar.get(Calendar.MILLISECOND)
//
//        return ("$ourHour, $ourMin, $ourSec, $ourMilisec")
//    }

    fun consumableImpact() : Double{
        val cow_data = if (cow_text.text.isNotEmpty()) cow_text.text.toString().toInt() else 0
        val chicken_data = if (chicken_text.text.isNotEmpty()) chicken_text.text.toString().toInt() else 0
        val pig_data = if (pig_text.text.isNotEmpty()) pig_text.text.toString().toInt() else 0

        val cowImpact = (cow_data/5.33) * 1799
        // cow_data is number of 3oz portions had. We need amount of pounds, so we divide by 5.33, as a pound is 16oz,
        // and there are 5.33 of 3oz in 16oz.
        // to produce 1lb of steak/beef takes 1,799 gallons of water
        val chickenImpact = (chicken_data/5.33) * 468 // to produce 1lb of poultry takes 468 gallons of water
        val pigImpact = (pig_data/5.33) * 576 // to produce 1lb of pork takes 576 gallons of water

        val waterFootprint = cowImpact + chickenImpact + pigImpact

        return waterFootprint
    }


}
