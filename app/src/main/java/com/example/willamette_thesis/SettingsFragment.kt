package com.example.willamette_thesis

import android.annotation.SuppressLint
import android.app.ProgressDialog.show
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.willamette_thesis.R.color.*
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_settings.view.*
import java.lang.reflect.Member.PUBLIC
import kotlin.reflect.KVisibility


class SettingsFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    private var PRIVATE_MODE = 0
    private val PREF_FILE = "com.theme.prefs"
    private val PREF_THEME = "theme-preference"


    //add the tag
    val TAG: String = "ECO-FR3ndly"
    val userPath = "/" + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE")


    @SuppressLint("ResourceAsColor", "WrongConstant")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)




        val settingsView = inflater.inflate(R.layout.activity_settings, container, false)

        //setContentView(R.layout.activity_settings)

        val prefs = this.activity?.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)


        settingsView.nature_button.setOnClickListener {

            //val data = hashMapOf("chosen_theme" to "nature")
            //db.collection(userPath).document("app_theme").set(data)

            val editor = prefs!!.edit()
            editor.putString(PREF_THEME, "nature")
            editor.apply()


            //val sharedPref: SharedPreferences? = activity?.getPreferences(PRIVATE_MODE)
            //with (sharedPref?.edit()) {
              //  this?.putString(PREF_NAME, "nature")
               // this?.commit()
           // }

            //val sharedPref = activity?.getPreferences(PUBLIC)
            //with (sharedPref?.edit()) {
              //  this?.putString(getString(R.string.themeNature), "nature")
                //this?.commit()

            Toast.makeText(activity, "Settings Preferences Applied to Nature", Toast.LENGTH_SHORT).show();

            }





        settingsView.original_button.setOnClickListener {

            val editor = prefs!!.edit()
            editor.putString(PREF_THEME, "original")
            editor.apply()


            Toast.makeText(activity, "Settings Preferences Applied to Original", Toast.LENGTH_SHORT).show();

        }

        settingsView.logout_btn.setOnClickListener {
            signOut()
            createSignInIntent()
        }

        return settingsView

    }// On Create



    private fun signOut() {
        // [START auth_fui_signout]
        FirebaseAuth.getInstance().signOut()
//        AuthUI.getInstance()
//            .signOut(this)
//            .addOnCompleteListener {
//                // ...
//            }
        // [END auth_fui_signout]
    }

    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
            //AuthUI.IdpConfig.FacebookBuilder().build()
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

