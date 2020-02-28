package com.example.willamette_thesis

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class AnalyticsActivity : AppCompatActivity(){

    lateinit var toolbar: ActionBar

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_waste -> {
                toolbar.title= "Waste"
                val wasteFragment = WasteDataFragment.newInstance()
                openFragment(wasteFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_transportation -> {
                toolbar.title= "Transportation"
                val transpoFragment = WasteDataFragment.newInstance()
                openFragment(transpoFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_consumable -> {
                toolbar.title= "Consumable"
                val consumableFragment = WasteDataFragment.newInstance()
                openFragment(consumableFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        //toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.dataNavigationView)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }


}