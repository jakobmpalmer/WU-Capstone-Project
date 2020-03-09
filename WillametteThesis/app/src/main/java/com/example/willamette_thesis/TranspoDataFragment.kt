package com.example.willamette_thesis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class TranspoDataFragment : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            //homeViewModel =
            //ViewModelProviders.of(this).get(HomeViewModel::class.java)
            //val root = inflater.inflate(R.layout.fragment_transpo, container, false)
            return inflater.inflate(R.layout.fragment_transpo, container, false)
            //val textView: TextView = root.findViewById(R.id.text_home)
            //homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
            //})
            //return root
        }

    /*
    Vehicle : distance (km/yr) /*EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Bus : distance (km/yr) * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Metro: distance (km/yr) * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Taxi: distance (km/yr) * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Rail: distance (km/yr) * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    Flying : distance (km/yr)* 1.09 * EF (kg CO2e/km) = emissions (kg CO2e/yr)
    */*/




}