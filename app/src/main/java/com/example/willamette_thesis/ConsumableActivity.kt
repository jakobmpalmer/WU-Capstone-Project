package com.example.willamette_thesis

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_consumable.*
import java.util.*

private val PREF_THEMES = "com.theme.prefs"

class ConsumableActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")

    private val appHome = HomeActivity()
    private var ourDate = appHome.getOurDate()
    private val ourSettings = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val themePrefs = this.getSharedPreferences(PREF_THEMES, Context.MODE_PRIVATE) ?: return
        ourSettings.changeTheme( themePrefs, this)
        setContentView(R.layout.activity_consumable)

        submitConsum_Button.setOnClickListener{ it: View? ->
            storeData(ourDate)
            Toast.makeText(this@ConsumableActivity, "Consumable info for the day has been recorded", Toast.LENGTH_SHORT).show()
        }

    } //Oncreate



    private fun storeData(ourDate:String){
        var cowData = if (cow_text.text.isNotEmpty()) cow_text.text.toString().toDouble() else 0.0
        var chickenData = if (chicken_text.text.isNotEmpty()) chicken_text.text.toString().toDouble() else 0.0
        var pigData = if (pig_text.text.isNotEmpty()) pig_text.text.toString().toDouble() else 0.0

        // converting portions to oz. as every portion has 3 oz
        cowData *= 3
        chickenData *= 3
        pigData *= 3


        db.collection("users").document(userPath).collection(ourDate).document("consumables").get().addOnSuccessListener {result ->

            cowData += if (result?.get("cow_oz") != null) result.get("cow_oz").toString().toDouble() else 0.0
            chickenData += if (result?.get("chicken_oz") != null) result.get("chicken_oz").toString().toDouble() else 0.0
            pigData += if (result?.get("pig_oz") != null) result.get("pig_oz").toString().toDouble() else 0.0


            val water_fp_consum = consumableImpact(cowData, chickenData, pigData)
            // water footprint from consumables (meat) in gallons of water used

            val data = hashMapOf(
                "cow_oz" to cowData,
                "chicken_oz" to chickenData,
                "pig_oz" to pigData,
                "water_fp_consum" to water_fp_consum

            )

            db.collection("users").document(userPath).collection(ourDate).document("consumables").set(data)

        }

    }

    fun consumableImpact(cow_oz: Double, chicken_oz: Double, pig_oz:Double) : Double{

        val cowImpact = (cow_oz/16) * 1799
        // cow_data is number oz had. We need amount of pounds, so we divide by 16, as a pound is 16oz,
        // to produce 1lb of steak/beef takes 1,799 gallons of water
        val chickenImpact = (chicken_oz/16) * 468 // to produce 1lb of poultry takes 468 gallons of water
        val pigImpact = (pig_oz/16) * 576 // to produce 1lb of pork takes 576 gallons of water

        val waterFootprint = cowImpact + chickenImpact + pigImpact

        return waterFootprint
    }

    fun changeTheme(){
        val sharedPref = this.getSharedPreferences("com.theme.prefs", Context.MODE_PRIVATE) ?: return
        val name = sharedPref.getString("theme-preference", "original")
        if (name == "nature") {
            setTheme(R.style.Blue)
        } else if (name == "original") {
            setTheme(R.style.AppTheme)
        }
    }
}
