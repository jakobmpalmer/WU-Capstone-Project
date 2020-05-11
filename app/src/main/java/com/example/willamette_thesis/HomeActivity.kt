package com.example.willamette_thesis

//import sun.jvm.hotspot.utilities.IntArray

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity() {

//    var enterWaste = false
//    var enterTravel = false
//    var enterConsum = false


    //lateinit var toolbar: ActionBar
    private val db = FirebaseFirestore.getInstance()
    private val PREF_FILE = "com.theme.prefs"
    private val PREF_THEME = "theme-preference"

    //var user = firebase.auth().currentUser;
    private val TAG = "Home Activity Problem"

    private val RC_SIGN_IN = 123

    private var ids: Array<String?>? = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000)
    private var pdt: SimpleTimeZone = SimpleTimeZone(-8 * 60 * 60 * 1000, ids?.get(0))
    private var calendar: Calendar = GregorianCalendar(this.pdt)

    //val homeFragment = home_fragment
//    val mainDataMenuItem: MenuItem = dataNavigationView.menu.getItem(0)
//    val mainLogMenuItem: MenuItem = dataNavigationView.menu.getItem(1)
//    val mainSettingMenuItem: MenuItem = dataNavigationView.menu.getItem(2)

    //private lateinit var appBarConfiguration: AppBarConfiguration
    //val appBarConfiguration = AppBarConfiguration(navController.graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = this.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)?: return
        val name = sharedPref.getString(PREF_THEME, "original")

        val settingsFrag = SettingsFragment()
        settingsFrag.changeTheme(sharedPref, this)
//        if (name == "nature"){
//            setTheme(R.style.Green)
//        }else if(name == "original"){
//            setTheme(R.style.AppTheme)
//        }

        setContentView(R.layout.activity_home)


        val bottomNavigation: BottomNavigationView = findViewById(R.id.botNavigationView)
        val navController: NavController = Navigation.findNavController(this, R.id.home_fragment)

        NavigationUI.setupWithNavController(bottomNavigation, navController)
        //navController.navigate(ScreenSlidePagerActivity().id)

    //Sign-in
        if(FirebaseAuth.getInstance().currentUser != null){
            println("Signed in as, " + FirebaseAuth.getInstance().currentUser)
        } else {
            //not signed in
            println("You are not signed in!")
            createSignInIntent()
        }

    } // OnCreate


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

    // [START auth_fui_result]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (requestCode == RC_SIGN_IN) {
//            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                createSignInIntent()
            }
        // [END auth_fui_result]
        }

//    private fun signOut() {
//        // [START auth_fui_signout]
//        AuthUI.getInstance()
//            .signOut(this)
//            .addOnCompleteListener {
//                // ...
//            }
//        // [END auth_fui_signout]
//    }

    fun printDisplayName(){

        println("Your Displayname: " + FirebaseAuth.getInstance().currentUser)
        println("Your Displayname: " + (FirebaseAuth.getInstance().currentUser?.email ?: "NOT AVAILABLE"))
    }

    public fun getOurDate() : String{
        var ourYear = calendar.get(Calendar.YEAR)
        var ourMonth = calendar.get(Calendar.MONTH)
        var ourDay = calendar.get(Calendar.DAY_OF_MONTH)

        //return ("$ourYear, $ourMonth, $ourDay")
        return ("$ourDay, $ourMonth, $ourYear")
    }

    fun getOurYear(): Int { return calendar.get(Calendar.YEAR) }
    fun getOurMonth(): Int { return calendar.get(Calendar.MONTH) }
    fun getOurDay(): Int { return calendar.get(Calendar.DAY_OF_MONTH) }

    public fun getOurTime() : String{
        var ourHour = calendar.get(Calendar.HOUR_OF_DAY)
        var ourMin = calendar.get(Calendar.MINUTE)
        var ourSec = calendar.get(Calendar.SECOND)
        var ourMilisec = calendar.get(Calendar.MILLISECOND)

        return ("$ourHour, $ourMin, $ourSec, $ourMilisec")
    }

    fun getUserEmail(): String{
        return (FirebaseAuth.getInstance().currentUser?.email ?: "Not Logged In")
    }

    fun getAccountCreationDate() : String? {
        var date =  Date(FirebaseAuth.getInstance().currentUser?.metadata!!.creationTimestamp.toLong())
        val simple_format = SimpleDateFormat("MM-dd-yyyy")
        val date_formatted = simple_format.format(date)

        return date_formatted

    }
}
