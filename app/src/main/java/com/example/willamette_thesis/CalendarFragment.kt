package com.example.willamette_thesis

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.willamette_thesis.calendar_fragments.TravelFragment
import com.example.willamette_thesis.calendar_fragments.WasteFragment
import com.example.willamette_thesis.calendar_fragments.WaterFPFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import java.util.*


private const val NUM_PAGES = 3

class CalendarFragment: Fragment() {

    private val DO_DEBUG = true

    private var ids: Array<String?>? = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000)
    private var pdt: SimpleTimeZone = SimpleTimeZone(-8 * 60 * 60 * 1000, ids?.get(0))
    private var calendar: Calendar = GregorianCalendar(this.pdt)

    private val appHome = HomeActivity()
    private val currentUser = appHome.getUserEmail()
    private val dateToday = appHome.getOurDate()


    val db = FirebaseFirestore.getInstance()
    private var todayDataRef = db.collection("users").document(currentUser).collection(dateToday)
//    private var selectedDateRef = todayDataRef

    companion object {
        var selectedDateRef = FirebaseFirestore.getInstance().collection("users")
    }
    //private var totalRef = todayDataRef.collection("total-data")

    //private val REF_PREF_FILE = "our-calref-prefs"
    private val REF_PREF_FILE = "com.calref.prefs"
    //val refPrefs = this.activity?.getSharedPreferences(this.REF_PREF_FILE, Context.MODE_PRIVATE)

    private lateinit var mPager: ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.data_display_layout)

        val calView: View = inflater.inflate(
            R.layout.fragment_calendar,
            container,
            false
        )

        val refPrefs = this.activity?.getSharedPreferences(this.REF_PREF_FILE, Context.MODE_PRIVATE)

        selectedDateRef = todayDataRef

        val tLayout = calView.metricTabLayout
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = calView.metricView
        println(tLayout.toString())

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        mPager.adapter = pagerAdapter

        TabLayoutMediator(tLayout, mPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Water"
                1 -> "Carbon"
                else -> "Trash"
            }
        }.attach()




        val cal = calView.calendarView
        cal.setDate(System.currentTimeMillis(), true, false)

//        var currentDateFormatted = System.currentTimeMillis().to
        var currentDateFormatted = cal.dateTextAppearance
        if (DO_DEBUG) println("$currentDateFormatted <---- currentDateFormatted")





        var currentDayStr: String

        cal.setOnDateChangeListener { view, year, month, dayOfMonth ->
            if (DO_DEBUG) println("changed dates!")
            var userMonth = month + 1
            //val msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
            //if (DO_DEBUG) Toast.makeText(this@CalendarFragment.context, msg, Toast.LENGTH_SHORT).show()
            //currentDayText.text = "$dayOfMonth/$userMonth/$year"
            currentDayStr = "$dayOfMonth, $userMonth, $year"
            if (DO_DEBUG) println("currentDayRef: $currentDayStr")

            storeFbRefPrefs(refPrefs,"ourCurrentDateStr", "$dayOfMonth, $month, $year")
            println("We just stored, $dayOfMonth, $month, $year")

            var eventOccursOn: Long = 0
            val c = Calendar.getInstance()
            c[year, month] = dayOfMonth
            eventOccursOn = c.timeInMillis //this is what you want to use later
            println("eOO: = $eventOccursOn")

            println("pre-update: $selectedDateRef")
            selectedDateRef = updateMyRefs(currentDayStr)
            println("post-update: $selectedDateRef")
            val newAdapter = ScreenSlidePagerAdapter(this)
            mPager.adapter = newAdapter

           cal.setDate(eventOccursOn, true, false)


        }
//SOLUTION: to get time in milisecs from jan1 1970
//        var eventOccursOn: Long = 0
//
//        cal.setOnDateChangeListener { _, year, month, day ->
//
//            //show the selected date as a toast
//            Toast.makeText(this.context, "$day/$month/$year", Toast.LENGTH_LONG).show()
//
//            val c = Calendar.getInstance()
//            c[year, month] = day
//            eventOccursOn = c.timeInMillis //this is what you want to use later
//            println("eOO: = $eventOccursOn")
//        }
//        println("eOO: = $eventOccursOn")

        return calView
    }

    fun getSelectedDate(): String{
        var ourYear = calendar.get(Calendar.YEAR)
        var ourMonth = calendar.get(Calendar.MONTH)
        var ourDay = calendar.get(Calendar.DAY_OF_MONTH)

        return ("$ourYear, $ourMonth, $ourDay")

    }

    private fun updateMyRefs(currentDay: String) : CollectionReference{
        //todayDataRef = db.collection("users").document(currentUser).collection(currentDay)
        var selectedDateFbRef = db.collection("users").document(currentUser).collection(currentDay)
        //totalRef = todayDataRef.collection("total-data")
        if (DO_DEBUG) println("updating ref, ${todayDataRef}")
        selectedDateRef = selectedDateFbRef
        return selectedDateFbRef

    }

    fun getSelectedDateRef(): CollectionReference {
        //return todayDataRef
        return selectedDateRef
    }



// CHANGE ?. to !!.
    fun storeFbRefPrefs(prefs: SharedPreferences?, prefDate:String, input:String ){
        println("prefDate:: $prefDate")
        println("Input:: $input")
        println("prefs:: ${prefs.toString()}")
        val editor = prefs!!.edit()
        editor.putString(prefDate, input)
        editor.apply()
    }



    private inner class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int): Fragment{
            when (position) {
                0 -> {
                    return WaterFPFragment()
                }
                1 -> {
                    return TravelFragment()
                }
                else -> {
                    return WasteFragment()
                }
            }
        }
    }

}
