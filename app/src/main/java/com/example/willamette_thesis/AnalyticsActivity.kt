package com.example.willamette_thesis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore


class AnalyticsActivity : AppCompatActivity(){

    //lateinit var toolbar: ActionBar
    private val db = FirebaseFirestore.getInstance()
    //var user = firebase.auth().currentUser;
    val TAG = "Analytics Problem"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)
        //setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayShowTitleEnabled(false)
            //setUpNavigation()
        //toolbar = supportActionBar!!

        val bottomNavigation: BottomNavigationView = findViewById(R.id.dataNavigationView)
        val navController: NavController = Navigation.findNavController(this, R.id.home_fragment)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

}