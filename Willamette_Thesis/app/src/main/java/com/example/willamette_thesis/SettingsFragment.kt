package com.example.willamette_thesis

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.willamette_thesis.R.*
import com.example.willamette_thesis.R.color.*
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_car.*
import kotlinx.android.synthetic.main.activity_car.view.*
import kotlinx.android.synthetic.main.activity_waste.*
import kotlinx.android.synthetic.main.activity_settings.view.*


class SettingsFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    //add the tag
    val TAG: String = "ECO-FR3ndly"

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        println("Creating settings!")

        var settingsView = inflater.inflate(layout.activity_settings, container, false)
        //setContentView(R.layout.activity_settings)
        val ct = ContextThemeWrapper(activity, R.style.Orange)
        val localInf = inflater.cloneInContext(ct)
        //val settingsViewAlt = localInf.inflate(layout.activity_settings, container, false)

        //took from here
        settingsView.nature_button.setOnClickListener {

            settingsView = localInf.inflate(layout.activity_settings, container, false)
        }
        settingsView.original_button.setOnClickListener {
            settingsView = inflater.inflate(layout.activity_settings, container, false)
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

