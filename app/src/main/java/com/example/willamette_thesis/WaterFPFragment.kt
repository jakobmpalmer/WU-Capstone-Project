package com.example.willamette_thesis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class WaterFPFragment : Fragment() {


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            val thisView = inflater.inflate(R.layout.waterfootprint_fragment, container, false)


            return thisView
        }


}