package com.example.slidingroot.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.slidingroot.R
import com.example.slidingroot.classes.MealData


class DetailsShowMovies : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_show_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.details_country_sm).text = MealData.getCountry()
        view.findViewById<TextView>(R.id.details_category_sm).text = MealData.getCategory()
        view.findViewById<TextView>(R.id.details_drink_sm).text = MealData.getDrink()
    }
}