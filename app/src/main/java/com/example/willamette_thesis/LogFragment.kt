package com.example.willamette_thesis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_logging.view.*



class LogFragment : Fragment() {

    @SuppressLint("WrongViewCast")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_logging)

        println("Entering Log Fragment.")


        val logView: View = inflater.inflate(
            R.layout.activity_logging,
            container,
            false
        )


//Profile
        logView.imageProf.setOnClickListener {
            val profileIntent = Intent(activity, ProfileActivity::class.java)
            startActivity(profileIntent)
        }

// Transportation
        logView.buttonTransportation.setOnClickListener{
            //val carImgIntent = Intent(this, CarActivity::class.java)
            val carImgIntent = Intent(activity, TravelActivity::class.java)
            startActivity(carImgIntent)
        }
//        val imageTransportation = findViewById <ImageView> (R.id.transportationIV)
        logView.transportationIV.setOnClickListener {
//            val carIntent = Intent(activity, LogNavigator::class.java)
            val carIntent = Intent(activity, TravelActivity::class.java)
            startActivity(carIntent)
        }


//Waste
        logView.buttonWaste.setOnClickListener{
            val wasteIntent = Intent(activity, WasteLogActivity::class.java)
            startActivity(wasteIntent)
        }
        //val imageWaste = findViewById <ImageView>(R.id.imageTrash)
        logView.imageWaste.setOnClickListener {
            val wasteIntent = Intent(activity, WasteLogActivity::class.java)
            startActivity(wasteIntent)
        }


//Consumable
        logView.buttonConsumption.setOnClickListener {
            val consumableIntent = Intent(activity, ConsumableActivity::class.java)
            startActivity(consumableIntent)
        }

        logView.imageApple.setOnClickListener {
            val appleIntent = Intent(activity, ConsumableActivity::class.java)
            startActivity(appleIntent)
        }

        return logView
    } //end on create


}
