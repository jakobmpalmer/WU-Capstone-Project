package com.example.willamette_thesis

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var waterCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        println("CREATED")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

//Water Display
        val waterTV = findViewById<TextView>(R.id.waterCountDisp)
        val waterBtn = findViewById<Button>(R.id.waterBtn)

        waterBtn.setOnClickListener {
            AddWater()
        }

//Spinner
        val waterSizes = resources.getStringArray(R.array.WaterSizes)

        val spinner = findViewById<Spinner>(R.id.waterSpinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, waterSizes
            )
            spinner.adapter = adapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun AddWater(){
        val WaterText = findViewById<TextView>(R.id.waterCountDisp)
        val waterStr: String = WaterText.text.toString()

        var count = waterStr.toInt()
        var ourWater = count + 1

        println("the count is $count")
        println("the water is $waterCount")
        println("the ourWater is $ourWater")
        waterCount = ourWater

        println("the watercount is $waterCount")
        println("the ourWater is $ourWater")

        WaterText.text = "$waterCount"
        var waterTotal = waterCount * waterSpinner.selectedItem.toString().toInt()
        waterTotalTV.text = "Total: $waterTotal"
    }


}
