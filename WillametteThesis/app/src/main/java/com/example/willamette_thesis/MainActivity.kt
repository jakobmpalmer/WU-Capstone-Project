package com.example.willamette_thesis

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

//import com.example.willamette_thesis.R.id.transportationIV

class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonTransportation = findViewById<Button>(R.id.buttonTransportation)
        buttonTransportation.setOnClickListener{
            val intent = Intent(this, carActivity::class.java)
            startActivity(intent)
        }

        val imageTransportation = findViewById(R.id.transportationIV) as ImageView
        imageTransportation.setOnClickListener {
            val intent = Intent(this, carActivity::class.java)
            startActivity(intent)
        }


        buttonWaste.setOnClickListener{
            val wasteIntent = Intent(this, wasteActivity::class.java)
            startActivity(wasteIntent)
        }

        val imageWaste = findViewById(R.id.imageTrash) as ImageView
        imageWaste.setOnClickListener {
            val wasteIntent = Intent(this, wasteActivity::class.java)
            startActivity(wasteIntent)
        }


        buttonConsumption.setOnClickListener {
            val consumableIntent = Intent(this, ConsumableActivity::class.java)
            startActivity(consumableIntent)
        }

        imageApple.setOnClickListener {
            val appleIntent = Intent(this, ConsumableActivity::class.java)
            startActivity(appleIntent)

        }



    }
}
