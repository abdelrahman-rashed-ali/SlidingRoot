package com.example.slidingroot.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slidingroot.R
import com.example.slidingroot.adaptors.ItemsAdaptor
import com.example.slidingroot.classes.MealData


class ItemsShowMeals : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items_show_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = view.findViewById<RecyclerView>(R.id.items_rv)
        rv.layoutManager = GridLayoutManager(view.context,3, RecyclerView.VERTICAL,false)
        rv.adapter = ItemsAdaptor(MealData.getItems())
    }


}