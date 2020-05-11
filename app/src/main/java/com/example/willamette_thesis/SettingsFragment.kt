package com.example.willamette_thesis

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_settings.view.*


class SettingsFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

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



        val spinner: Spinner = settingsView.spinner
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this.context,
            R.array.style_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }



        settingsView.nature_button.setOnClickListener {

            storePrefs(prefs, PREF_THEME, "nature")
            Toast.makeText(activity, "Settings Preferences Applied to Nature", Toast.LENGTH_SHORT).show();

            //val ft= fragmentManager!!.beginTransaction()
            //if (Build.VERSION.SDK_INT >= 26) {
            //    ft.setReorderingAllowed(false)
            //}
            //ft.detach(this).attach(this).commit()
            val profileIntent = Intent(activity, HomeActivity::class.java)
            startActivity(profileIntent)

            }

        settingsView.original_button.setOnClickListener {

            storePrefs(prefs, PREF_THEME, "original")
            Toast.makeText(activity, "Settings Preferences Applied to Original", Toast.LENGTH_SHORT).show();
            val profileIntent = Intent(activity, HomeActivity::class.java)
            startActivity(profileIntent)
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

    fun storePrefs(prefs: SharedPreferences?, prefName:String, input:String ){
        val editor = prefs!!.edit()
        editor.putString(prefName, input)
        editor.apply()
    }

    fun changeTheme(sharedPref: SharedPreferences, thisContext: Context){
        //val sharedPref = this.getSharedPreferences("com.theme.prefs", Context.MODE_PRIVATE) ?: return
        val name = sharedPref.getString("theme-preference", "original")
        if (name == "nature") {
            thisContext.setTheme(R.style.Orange)
        } else if(name == "dark"){
            thisContext.setTheme(R.style.Dark)
        }else if (name == "original") {
            thisContext.setTheme(R.style.AppTheme)
        }
    }



    inner class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Another interface callback
        }
    }

}

