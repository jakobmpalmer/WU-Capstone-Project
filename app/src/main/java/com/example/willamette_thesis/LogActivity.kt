package com.example.willamette_thesis

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_logging.*


//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase


//import com.example.willamette_thesis.R.id.transportationIV

class LogActivity : AppCompatActivity() {

    // Choose an arbitrary request code value
    private val RC_SIGN_IN = 123

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logging)

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
            val wasteIntent = Intent(this, WasteActivity::class.java)
            startActivity(wasteIntent)
        }

        val imageWaste = findViewById <ImageView>(R.id.imageWaste)
        imageWaste.setOnClickListener {
            val wasteIntent = Intent(this, WasteActivity::class.java)
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

//        settings_image.setOnClickListener {
//            signOut()
//            createSignInIntent()
//        }



        //Firebase
        //val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        //var ref: DatabaseReference = database.getReference("server/saving-data/fireblog")
        //*-*-*
//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//            println("Signed in as, " + FirebaseAuth.getInstance().getCurrentUser())
//        } else {
//            //not signed in
//            println("You are not signed in!")
//            createSignInIntent()
//        }

    } //end on create


//
//    private fun createSignInIntent() {
//        // [START auth_fui_create_intent]
//        // Choose authentication providers
//        val providers = arrayListOf(
//            AuthUI.IdpConfig.EmailBuilder().build(),
//            AuthUI.IdpConfig.GoogleBuilder().build(),
//            AuthUI.IdpConfig.FacebookBuilder().build()
//        )
//
//        // Create and launch sign-in intent
//        startActivityForResult(
//            AuthUI.getInstance()
//                .createSignInIntentBuilder()
//                .setAvailableProviders(providers)
//                .build(),
//            //RC_SIGN_IN)
//            1)
//        // [END auth_fui_create_intent]
//    }
//
//    // [START auth_fui_result]
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) {
//            val response = IdpResponse.fromResultIntent(data)
//
//            if (resultCode == Activity.RESULT_OK) {
//                // Successfully signed in
//                val user = FirebaseAuth.getInstance().currentUser
//                // ...
//            } else {
//                // Sign in failed. If response is null the user canceled the
//                // sign-in flow using the back button. Otherwise check
//                // response.getError().getErrorCode() and handle the error.
//                // ...
//            }
//        }
//    }
//    // [END auth_fui_result]
//
//
//    private fun signOut() {
//        // [START auth_fui_signout]
//        AuthUI.getInstance()
//            .signOut(this)
//            .addOnCompleteListener {
//                // ...
//            }
//        // [END auth_fui_signout]
//    }


}
