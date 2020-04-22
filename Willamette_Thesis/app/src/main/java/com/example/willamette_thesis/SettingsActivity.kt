package com.example.willamette_thesis

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.willamette_thesis.R.color.*
import com.firebase.ui.auth.AuthUI
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_car.*
import kotlinx.android.synthetic.main.activity_waste.*

class SettingsActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    //add the tag
    val TAG: String = "ECO-FR3ndly"

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        nature_button.setOnClickListener {

            textView8.setTextColor(natureSettings_text)
            textView9.setTextColor(natureSettings_text)

            //transportation_text.setTextColor(natureCar_text)
            transpoActivityTitle.setTextColor(natureCar_text)
            //miles_text.setTextColor(natureCar_text)
            car_text.setTextColor(natureCar_text)
            bus_text.setTextColor(natureCar_text)
            plane_text.setTextColor(natureCar_text)
            walk_text.setTextColor(natureCar_text)

            textView3.setTextColor(natureWaste_text)
            textView4.setTextColor(natureWaste_text)
            plastic_text.setTextColor(natureWaste_text)
            recycle_text.setTextColor(natureWaste_text)
            trash_text.setTextColor(natureWaste_text)
        }

        original_button.setOnClickListener {
            textView8.setTextColor(originalSettings_text)
            textView9.setTextColor(originalSettings_text)

            //transportation_text.setTextColor(originalCar_text)
            transpoActivityTitle.setTextColor(originalCar_text)
            //miles_text.setTextColor(originalCar_text)
            car_text.setTextColor(originalCar_text)
            bus_text.setTextColor(originalCar_text)
            plane_text.setTextColor(originalCar_text)
            walk_text.setTextColor(originalCar_text)

            textView3.setTextColor(colorPrimary)
            textView4.setTextColor(colorPrimary)
            plastic_text.setTextColor(colorPrimary)
            recycle_text.setTextColor(colorPrimary)
            trash_text.setTextColor(colorPrimary)
        }

        logout_btn.setOnClickListener {
            signOut()
            createSignInIntent()
        }

    }// On Create



    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // ...
            }
        // [END auth_fui_signout]
    }

    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
        )

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            //RC_SIGN_IN)
            1)
        // [END auth_fui_create_intent]
    }
}

