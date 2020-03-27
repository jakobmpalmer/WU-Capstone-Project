package com.example.willamette_thesis

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

public class HomeActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 123

    private var ids: Array<String?>? = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000)
    private var pdt: SimpleTimeZone = SimpleTimeZone(-8 * 60 * 60 * 1000, ids?.get(0))
    private var calendar: Calendar = GregorianCalendar(this.pdt)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val loggingBtn = findViewById<Button>(R.id.logging_btn)
        val analyticsBtn = findViewById<Button>(R.id.analytics_btn)
        val settingsBtn = findViewById<Button>(R.id.settings_btn)

        loggingBtn.setOnClickListener{
            val logIntent = Intent(this, LogActivity::class.java)
            startActivity(logIntent)
        }

        analyticsBtn.setOnClickListener{
            val analyticsIntent = Intent(this, AnalyticsActivity::class.java)
            startActivity(analyticsIntent)
        }

        settingsBtn.setOnClickListener{
            val settingsIntent = Intent(this, SettingsActivity()::class.java)
            startActivity(settingsIntent)
        }


        settings_image.setOnClickListener {
            signOut()
            createSignInIntent()
        }


        if(FirebaseAuth.getInstance().currentUser != null){
            println("Signed in as, " + FirebaseAuth.getInstance().currentUser)
        } else {
            //not signed in
            println("You are not signed in!")
            createSignInIntent()
        }



    } // Oncreate


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

    // [START auth_fui_result]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
    // [END auth_fui_result]


    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // ...
            }
        // [END auth_fui_signout]
    }

    fun printDisplayName(){

        println("Your Displayname: " + FirebaseAuth.getInstance().currentUser)
        println("Your Displayname: " + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE"))
    }

    public fun getOurDate() : String{
        var ourYear = calendar.get(Calendar.YEAR)
        var ourMonth = calendar.get(Calendar.MONTH)
        var ourDay = calendar.get(Calendar.DAY_OF_MONTH)

        return ("$ourYear, $ourMonth, $ourDay")
    }

    public fun getOurTime() : String{
        var ourHour = calendar.get(Calendar.HOUR_OF_DAY)
        var ourMin = calendar.get(Calendar.MINUTE)
        var ourSec = calendar.get(Calendar.SECOND)
        var ourMilisec = calendar.get(Calendar.MILLISECOND)

        return ("$ourHour, $ourMin, $ourSec, $ourMilisec")
    }


}