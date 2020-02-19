package com.example.willamette_thesis

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_consumable.*
import kotlinx.android.synthetic.main.content_main.*


class ConsumableActivity : AppCompatActivity() {

    private var waterCount = 0

    object OurVariables {
        var waterTotal = 0
        var gasTotal = 0
        var ml: Double = 0.0
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        println("CREATED")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumable)
        setSupportActionBar(toolbar)

        dataDisplayTable.visibility = View.INVISIBLE

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Table Still There?", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            fabAction()
        }

// Water Display
// val waterTV = findViewById<TextView>(R.id.waterCountDisp)
        val waterBtn = findViewById<Button>(R.id.waterButton)

        waterBtn.setOnClickListener {
            waterTotalTV.text = addWater().toString()
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

        //Gas Display

        val gasBtn = findViewById<Button>(R.id.gasButton)
        gasBtn.setOnClickListener {
            gasCountDisplay.text = addGas()
            gasTotalTV.text = addGas()
        }

//Change Activity
        val gotoDDBtn = findViewById<Button>(R.id.changeActivityBtn)

        gotoDDBtn.setOnClickListener {
            println("Going To Data Display!!!")
            val ddIntent = Intent(this@ConsumableActivity, DataDisplay::class.java)
            startActivity(ddIntent)
        }


    } //Oncreate


    private fun fabAction(){
        val ddt = findViewById<TableLayout>(R.id.dataDisplayTable)
            if(ddt.isInvisible){
                println("- - ddt is invisible - -")
                ddt.visibility = View.VISIBLE
            } else{
                println("- - ddt is visible - -")
                ddt.visibility = View.INVISIBLE
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

/*Add Water Function
* Currently uses to many variables
* -Figure out whats going on while directly refrencing
* textview on WaterText line (81 currently)*/
    private fun addWater(): String {
        val waterText = findViewById<TextView>(R.id.waterCountDisp)
        val waterStr: String = waterText.text.toString()
        var currentTotal: Int = waterTotalTV.text.toString().split(" ").first().toInt()

        var count = waterStr.toInt()
        var ourCount = count + 1
        waterCount = ourCount
        waterCountDisp.text = "$waterCount"

        var ourSpinner = waterSpinner.selectedItem.toString()
        println("ourSpinnerr: $ourSpinner")
        var ourSpinnerValue = ourSpinner.split(" ").first().toInt()
        println("ourSpinnerValue: $ourSpinnerValue")
        var waterTotal = currentTotal + ourSpinnerValue

        mlTotalTV.text = calcML(waterTotal.toDouble()).toString() + " ml"

        OurVariables.waterTotal = waterTotal
        return waterTotal.toString() + " oz"
    }


    private fun addGas(): String {
        //val gasText = findViewById<TextView>(R.id.gasCountDisplay)
        //val gasStr: String = this.gasCountDisplay.toString()
        var currentTotal = gasTotalTV.text.toString().toInt()

        if(mileageInput.getText().toString() == ("")) {
            println("\n\tMileageStr == \"\"!\n")
            return "0"
        }

        //val mileageText = findViewById<EditText>(R.id.mileageInput)
        val mileageStr: String = mileageInput.getText().toString()
        val mileageInput = mileageStr.toInt()
        var gasCount = currentTotal + mileageInput

        OurVariables.gasTotal = gasCount
        return gasCount.toString()
    }

    private fun calcML(ounces: Double): Double {
        var mlVolValue: Double = 29.574
        var ourML = mlVolValue * ounces

        var finalML:Double = String.format("%.3f", ourML).toDouble()

        OurVariables.ml = finalML
        return finalML
    }


}
