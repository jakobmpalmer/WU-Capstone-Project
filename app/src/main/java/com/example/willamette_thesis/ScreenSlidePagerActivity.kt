package com.example.willamette_thesis

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.data_display_layout.*
import kotlinx.android.synthetic.main.data_display_layout.view.*

private const val NUM_PAGES = 2

@SuppressLint("Registered")
//class ScreenSlidePagerActivity: FragmentActivity() {
class ScreenSlidePagerActivity: Fragment() {


    private lateinit var mPager: ViewPager2

    //override fun onCreate(savedInstanceState: Bundle?) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.data_display_layout)

        val dataDisplayView: View = inflater.inflate(
            R.layout.data_display_layout,
            container,
            false
        )

        //TabLayout
        val tLayout = dataDisplayView.dataTabLayout

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = dataDisplayView.dataPager
        val pagerAdapter = ScreenSlidePagerAdapter(this)


        mPager.adapter = pagerAdapter

        TabLayoutMediator(tLayout, mPager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
            tab.text = if(position == 0) "Today" else "Calendar"
        }.attach()

        return dataDisplayView
    }


//    override fun onBackPressed() {
//        if (mPager.currentItem == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed()
//        } else {
//            // Otherwise, select the previous step.
//            mPager.currentItem = mPager.currentItem - 1
//        }
//    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int): Fragment{
            if(position == 0){
                return TodayDataFragment()
            } else if (position == 1) {
                return CalendarFragment()
            } else {
                return TodayDataFragment()
            }
        }
    }

}