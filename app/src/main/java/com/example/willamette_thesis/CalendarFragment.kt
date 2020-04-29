package com.example.willamette_thesis

import android.R.attr.maxDate
import android.R.attr.minDate
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.DatePicker.OnDateChangedListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import java.util.*


class CalendarFragment: Fragment() {

    private val DO_DEBUG = true

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

        if (DO_DEBUG) println("\n\n\n\tEntering Calendar Fragment")
        println("")

        val cal = CalendarView(this.context)
        cal.setDate(System.currentTimeMillis(), true, false)
        var aDate = cal.date

//        cal.setOnDateChangeListener { cal, year, month, dayOfMonth ->
//            println("changed dates! $dayOfMonth")
//        }

//        cal.setOnDateChangeListener(CalendarView.OnDateChangeListener().onSelectedDayChange(_, year, month, day) {
//                println("changed dates!")
//        })
//
//
//        OnDateChangeListener(cal, year, month, day).onSelectedDayChange(_, year, month, day) {
//            println("changed dates!")
//        })
//
//        datePicker.init(currentCalendar.get(Calendar.YEAR),
//            currentCalendar.get(Calendar.MONTH),
//            currentCalendar.get(Calendar.DAY_OF_MONTH),
//            OnDateChangedListener { view, selYear, selMonth, selDay ->
//                val dateSelectionType: String = getDateSelectionType(
//                    minDate.getTimeInMillis(),
//                    maxDate.getTimeInMillis(), selYear, selMonth, selDay
//                )
//                if (dateSelectionType == "IS_BETWEEN_RANGE") {
//                    return@OnDateChangedListener
//                }
//                if (dateSelectionType == "IS_LESS_THAN_MIN") {
//                    datePicker.updateDate(
//                        minDate[Calendar.YEAR], minDate[Calendar.MONTH],
//                        minDate[Calendar.DAY_OF_MONTH]
//                    )
//                    return@OnDateChangedListener
//                }
//                if (dateSelectionType == "IS_MORE_THAN_MAX") {
//                    datePicker.updateDate(
//                        maxDate[Calendar.YEAR],
//                        maxDate[Calendar.MONTH],
//                        maxDate[Calendar.DAY_OF_MONTH]
//                    )
//                    return@OnDateChangedListener
//                }
//            })

//        cal.setOnDateChangeListener { view, year, month, dayOfMonth ->
//            println("cal.date == aDate")
//            val v = cal.date
//            println("$v == $aDate")
//            if (cal.date != aDate) {
//                aDate = cal.date
//                Toast.makeText(
//                    view.context,
//                    "Year=$year Month=$month Day=$dayOfMonth",
//                    Toast.LENGTH_LONG
//                ).show()
//                println("changed dates!")
//                //cal.setBackgroundColor(Color.RED);
//            }
//        }
        var eventOccursOn: Long = 0

        cal.setOnDateChangeListener { _, year, month, day ->

            //show the selected date as a toast
            Toast.makeText(
                this.context,
                "$day/$month/$year",
                Toast.LENGTH_LONG
            ).show()
            val c = Calendar.getInstance()
            c[year, month] = day
            eventOccursOn = c.timeInMillis //this is what you want to use later
            println("eOO: = $eventOccursOn")
        }

        println("eOO: = $eventOccursOn")

//
//        val ourDate = appHome.getOurDate()
//        val ourTime = appHome.getOurTime()
//
//        println("Our current date: $ourDate")
//        val miliDate = calendar.timeInMillis
//        println("Attempting to set, $miliDate")
//        calendarView.date = (miliDate)
//        //ourCal.date = miliDate
//        println("We set, $miliDate")
//        calBtn.setOnClickListener {
//            println("Calbtn pressed..")
//            println(cal.dateTextAppearance)
//        }




        return calView
    }
}