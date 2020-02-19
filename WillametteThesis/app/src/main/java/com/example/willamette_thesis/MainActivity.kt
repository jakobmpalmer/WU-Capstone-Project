package com.example.willamette_thesis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


//import com.example.willamette_thesis.R.id.transportationIV

class MainActivity : AppCompatActivity() {



    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonTransportation = findViewById<Button>(R.id.buttonTransportation)
        buttonTransportation.setOnClickListener{
            val carImgIntent = Intent(this, CarActivity::class.java)
            startActivity(carImgIntent)
        }

        val imageTransportation = findViewById <ImageView> (R.id.transportationIV)
        imageTransportation.setOnClickListener {
            val carIntent = Intent(this, CarActivity::class.java)
            startActivity(carIntent)
        }


        buttonWaste.setOnClickListener{
            val wasteIntent = Intent(this, wasteActivity::class.java)
            startActivity(wasteIntent)
        }

        val imageWaste = findViewById <ImageView>(R.id.imageTrash)
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



        //Firebase
        //val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        //var ref: DatabaseReference = database.getReference("server/saving-data/fireblog")
        //*-*-*


    } //end main


}
