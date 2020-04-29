package com.example.willamette_thesis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.util.*


class CalendarFragment: Fragment() {

    private var ids: Array<String?>? = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000)
    private var pdt: SimpleTimeZone = SimpleTimeZone(-8 * 60 * 60 * 1000, ids?.get(0))
    private var calendar: Calendar = GregorianCalendar(this.pdt)

    private val appHome = HomeActivity()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.data_display_layout)

        val calView: View = inflater.inflate(
            R.layout.fragment_calendar,
            container,
            false
        )


        val ourCal = calendarView

        val ourDate = appHome.getOurDate()
        val ourTime = appHome.getOurTime()

        println("Our current date: $ourDate")
        val miliDate = calendar.timeInMillis
        println("Attempting to set, $miliDate")
        ourCal.setDate(miliDate, true, false)
        println("We set, $miliDate")





        return calView
    }
}