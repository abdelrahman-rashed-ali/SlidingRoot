package com.example.slidingroot.interfaces

import com.example.slidingroot.domains.Meals

interface HomeFragmentListener {
    fun onCategoryClick(category : String)
    fun onMealClick(meal : Meals)
}