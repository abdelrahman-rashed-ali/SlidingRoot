package com.example.slidingroot.ViewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.slidingroot.classes.retrofit
import com.example.slidingroot.domains.Categories
import com.example.slidingroot.domains.Favourite
import com.example.slidingroot.domains.Meals
import com.example.slidingroot.interfaces.DatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(private val dao: DatabaseDao , private val retrofit: retrofit) : ViewModel() {
    private var created = false

    // LiveData for Random Meal
    private val _meal: MutableLiveData<Meals> = MutableLiveData()
    val meal: LiveData<Meals> = _meal


    // LiveData for Categories
    private val _categories: MutableLiveData<List<Categories>> = MutableLiveData()
    val categories: LiveData<List<Categories>> = _categories

    // LiveData for Meals by Category
    private val _mealsByCategory: MutableLiveData<List<Meals>> = MutableLiveData()
    val mealsByCategory: LiveData<List<Meals>> = _mealsByCategory

    // LiveData for Network Status
    private val _networkAvailable: MutableLiveData<Boolean> = MutableLiveData()
    val networkAvailable: LiveData<Boolean> = _networkAvailable

    // LiveData for Get Meal By ID
    private val _mealsByID: MutableLiveData<Meals> = MutableLiveData()
    val mealsByID: LiveData<Meals> = _mealsByID



    @SuppressLint("NullSafeMutableLiveData")
    fun fetchRandomMeal() {
        if(!created){
            created = true
            viewModelScope.launch(Dispatchers.IO) {
                while(true){
                    try {
                        val newMeal = retrofit.Service.getRandomMeal()
                        val responseData = newMeal.body()!!
                        _meal.postValue(responseData.meals[0])
                    } catch (ex: Exception) {
                        _meal.postValue(null) // Handle failure case
                    }
                    Thread.sleep(10000)
                }

            }
        }
    }

    fun fetchCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = retrofit.Service.getCategories()
                _categories.postValue(data.body()?.categories ?: listOf())
            } catch (ex: Exception) {
                _categories.postValue(listOf()) // Handle failure case
            }
        }
    }

    // Fetch Meals by Category
    fun fetchMealsByCategory(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = retrofit.Service.getByCategory(categoryName)
                _mealsByCategory.postValue(data.body()?.meals ?: listOf())
            } catch (ex: Exception) {
                _mealsByCategory.postValue(listOf()) // Handle failure case
            }
        }
    }

    // Fetch meal by ID
    @SuppressLint("NullSafeMutableLiveData")
    fun fetchMealByID(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = retrofit.Service.getByID(id)
                _mealsByID.postValue(data.body()?.meals?.get(0))
            } catch (ex: Exception) {
                _mealsByID.postValue(null) // Handle failure case
            }
        }
    }




    // Favourite Fragment
    private val _favourites = MutableLiveData<List<Favourite>>()
    val favourites: LiveData<List<Favourite>> = _favourites

    fun fetchFavourites(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val allFav = dao.getAllFavouritesByEmail(email)
            withContext(Dispatchers.Main) {
                _favourites.value = allFav
            }
        }
    }
}