package com.example.willamette_thesis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class LogNavigator: AppCompatActivity()  {

    val logHome = LogNavigator()
    val appHome = HomeActivity()

    override fun onCreate(savedInstanceState: Bundle?){
            super.onCreate(savedInstanceState)
        println("Creating Log Navigator")
        val logBottomNavigation: BottomNavigationView = findViewById(R.id.loggingNavBar)
        val logNavController: NavController = Navigation.findNavController(this, R.id.logFragment)
        NavigationUI.setupWithNavController(logBottomNavigation, logNavController)

        println("Entering...")
        when {
            appHome.enterWaste -> {
                println("ActivityWaste" + appHome.enterWaste)
                setContentView(R.layout.activity_waste)
            }
            appHome.enterConsum -> {
                setContentView(R.layout.activity_consumable)
            }
            else -> {
                println("ActivityTravel" + appHome.enterTravel)
                setContentView(R.layout.activity_car)
            }
        }


    }

}