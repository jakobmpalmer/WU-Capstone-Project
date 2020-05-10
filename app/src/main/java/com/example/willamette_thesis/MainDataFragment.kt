package com.example.willamette_thesis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.main_data_fragment.view.*


class MainDataFragment : Fragment() {

    private lateinit var consumpPage: ViewPager2
    private lateinit var transpoPage: ViewPager2
    private lateinit var waterPage: ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val mainDataView = inflater.inflate(R.layout.main_data_fragment,
                                               container,
                                   false)



        waterPage = mainDataView.waterFpPager
        consumpPage = mainDataView.trashPager
        transpoPage = mainDataView.transpoPager

        //waterPage.currentItem = MyPagerAdapter(this).getItem(0)



        return mainDataView
    }



//    private inner class MyPagerAdapter(fm: FragmentManager?) : FragmentStateAdapter(fm) {
//        override fun getItem(pos: Int): Fragment {
//            return when (pos) {
//                0 -> WaterFPFragment.newInstance("FirstFragment, Instance 1")
//                1 -> SecondFragment.newInstance("SecondFragment, Instance 1")
//
//                else -> ThirdFragment.newInstance("ThirdFragment, Default")
//            }
//        }
//
//        override fun getCount(): Int {
//            return 3
//        }
//    }


}