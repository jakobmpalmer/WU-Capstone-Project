package com.example.willamette_thesis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //val loggingBtn = findViewById<Button>(R.id.logging_btn)
        //val settingsBtn = findViewById<Button>(R.id.settings_btn)

        loggingBtn.setOnClickListener{
            val carImgIntent = Intent(this, CarActivity::class.java)
            startActivity(carImgIntent)
        }

        settingsBtn.setOnClickListener{
            val carImgIntent = Intent(this, CarActivity::class.java)
            startActivity(carImgIntent)
        }

    }


}