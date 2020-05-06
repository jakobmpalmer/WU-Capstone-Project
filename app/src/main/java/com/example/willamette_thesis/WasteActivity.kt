package com.example.willamette_thesis


import android.content.Context
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
    val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")


    private var ids: Array<String?>? = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000)
    private var pdt: SimpleTimeZone = SimpleTimeZone(-8 * 60 * 60 * 1000, ids?.get(0))

    val appHome = HomeActivity()
    val ourDate = appHome.getOurDate()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeTheme()
        setContentView(R.layout.activity_waste)

        val submitWasteBtn = findViewById<Button>(R.id.submitWaste_Button)
        submitWasteBtn.setOnClickListener {

            storeData(ourDate)
            Toast.makeText(this@WasteActivity, "Waste info for the day has been recorded", Toast.LENGTH_SHORT).show()
        }
    }

    fun storeData(ourDate:String){
        var plasticData = if (plastic_text.text.isNotEmpty()) plastic_text.text.toString().toDouble() else 0.0
        var recycleData = if (recycle_text.text.isNotEmpty()) recycle_text.text.toString().toDouble() else 0.0
        var trashData = if (trash_text.text.isNotEmpty()) trash_text.text.toString().toDouble() else 0.0

        trashData = trash_lbs(trashData)


        db.collection("users").document(userPath).collection(ourDate).document("waste").get().addOnSuccessListener {result ->

            plasticData += result?.get("plastic_items").toString().toDouble()
            recycleData += result?.get("recycled_items").toString().toDouble()
            trashData += result?.get("trash_lbs").toString().toDouble()


            val water_ft_plastic = plasticImpact(plasticData,recycleData)
            // water footprint from plastic impact in gallons of water used

            val data = hashMapOf(
                "plastic_items" to plasticData,
                "recycled_items" to recycleData,
                "trash_lbs" to trashData,
                "water_ft_plastic" to water_ft_plastic
            )

            db.collection("users").document(userPath).collection(ourDate).document("waste").set(data)

        }
    }

    fun plasticImpact(plastic:Double, recycling:Double):Double{
        val impact = ((plastic - recycling)* 20.0 * 2.0)/128
        // Idicates gallons of water wasted. Assumes average 20oz water bottle size. Multiplying by two as producing plastic
        // bottle takes about double they size in water. We divide by 128 as there are 128 oz in a gallon of water

        return impact // water footprint from plastic in Oz
    }

    fun trash_lbs(trash_bags:Double): Double{
        val trashImpact = trash_bags * 22.0
        return trashImpact
    }

    fun changeTheme(){
        val sharedPref = this.getSharedPreferences("com.theme.prefs", Context.MODE_PRIVATE) ?: return
        val name = sharedPref.getString("theme-preference", "original")
        if (name == "nature") {
            setTheme(R.style.Pink)
        } else if (name == "original") {
            setTheme(R.style.AppTheme)
        }
    }
}
