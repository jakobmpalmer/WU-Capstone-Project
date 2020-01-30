package com.example.progress

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.progress.R.id.imageView2

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

        val imageTransportation = findViewById(R.id.imageView2) as ImageView
        imageTransportation.setOnClickListener {
            val intent = Intent(this, carActivity::class.java)
            startActivity(intent)
        }

    }
}
