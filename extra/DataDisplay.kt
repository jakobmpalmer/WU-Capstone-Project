package com.example.willamette_thesis
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.gasTotalTV
import kotlinx.android.synthetic.main.content_main.mlTotalTV

class DataDisplay : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_display)

        updateVars()
    }

    fun updateVars(){
        var mlVal = ConsumableActivity.OurVariables.ml
        var gasVal = ConsumableActivity.OurVariables.gasTotal
        var waterVal = ConsumableActivity.OurVariables.waterTotal

        mlTotalTV.text = mlVal.toString() + " millileters"
        gasTotalTV.text = gasVal.toString() + " miles"
        waterTotalTV.text = waterVal.toString() + " oz"
    }

}