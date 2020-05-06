package com.example.willamette_thesis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.data_display_layout.view.*
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
    private var todayDataRef = db.collection(currentUser).document(dateToday)
    private var totalRef = todayDataRef.collection("total-data")



    private lateinit var mPager: ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.data_display_layout)

        val calView: View = inflater.inflate(
            R.layout.fragment_calendar,
            container,
            false
        )

        if (DO_DEBUG) println("\n\n\n\tEntering Calendar Fragment")


        val tLayout = calView.dataTabLayout

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = calView.metricView
        val pagerAdapter = ScreenSlidePagerAdapter(this)


        mPager.adapter = pagerAdapter
        TabLayoutMediator(tLayout, mPager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
            tab.text = when (position) {
                0 -> "Waste"
                1 -> "Transportation"
                else -> "Consumable"
            }
        }.attach()




        val cal = calView.calendarView
        cal.setDate(System.currentTimeMillis(), true, false)

//        var currentDateFormatted = System.currentTimeMillis().to
        var currentDateFormatted = cal.dateTextAppearance
        if (DO_DEBUG) println("$currentDateFormatted <---- currentDateFormatted")





        var currentDayRef = dateToday
        //val currentDayText = calView.currentDateText
        //currentDayText.text = "${appHome.getOurDay()}/${appHome.getOurMonth() + 1}/${appHome.getOurYear()}"
        cal.setOnDateChangeListener { view, year, month, dayOfMonth ->
            if (DO_DEBUG) println("changed dates!")
            var userMonth = month + 1
            val msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
            if (DO_DEBUG) Toast.makeText(this@CalendarFragment.context, msg, Toast.LENGTH_SHORT).show()
            //currentDayText.text = "$dayOfMonth/$userMonth/$year"
            currentDayRef = "$dayOfMonth,$userMonth,$year"
            if (DO_DEBUG) println("currentDayRef: $currentDayRef")

            if (DO_DEBUG) println("todayDataRef before: {${todayDataRef.toString()}}")
            updateMyRefs(currentDayRef)
            if (DO_DEBUG) println("todayDataRef After: {${todayDataRef.toString()}}")
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

    private fun updateMyRefs(currentDay: String){
        todayDataRef = db.collection(currentUser).document(currentDay)
        totalRef = todayDataRef.collection("total-data")
        if (DO_DEBUG) println("updating ref, ${todayDataRef.toString()}")
    }



    private inner class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int): Fragment{
            when (position) {
                0 -> {
                    return plasticsFragment()
                }
                1 -> {
                    return plasticsFragment()
                }
                2 -> {
                    return CalendarFragment()
                }
                else -> {
                    return TodayDataFragment()
                }
            }
        }
    }

}
