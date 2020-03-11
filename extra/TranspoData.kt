package com.example.willamette_thesis

//import com.google.firebase.database.IgnoreExtraProperties

// [START blog_user_class]
//@IgnoreExtraProperties
data class TranspoData (
    var id: String? = "",
    //var transpo_data: Array<Int> = arrayOf<Int>()
    //var transpo_data: String = ""
    var car_miles: Int,
    var bus_miles: Int,
    var plane_miles: Int,
    var walked_miles: Int
)
// [END blog_user_class]