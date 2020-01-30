package com.example.progress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT


class carActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        val button = findViewById<Button>(R.id.button1)
        button.setOnClickListener{
            Toast.makeText(this@carActivity, "Transportation info for the day has been recorded", LENGTH_SHORT).show()
        }
    }
}
