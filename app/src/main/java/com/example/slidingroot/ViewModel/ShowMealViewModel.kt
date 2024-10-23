package com.example.slidingroot.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.slidingroot.classes.ConvertData
import com.example.slidingroot.classes.MealData
import com.example.slidingroot.database.LocalDatabase
import com.example.slidingroot.domains.Favourite
import com.example.slidingroot.domains.Meals
import com.example.slidingroot.interfaces.DatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowMealViewModel(application: Application) : AndroidViewModel(application) {
    private val databaseDao: DatabaseDao = LocalDatabase.getInstance(application).databaseDao()
    private val _isFavourite = MutableLiveData<Boolean>()
    val isFavourite: LiveData<Boolean> get() = _isFavourite
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    private lateinit var email: String
    private lateinit var allFavouritesIDs: List<Favourite>

    fun init(email: String) {
        this.email = email
        getAllFavouritesIDs()
    }

    private fun getAllFavouritesIDs() {
        viewModelScope.launch(Dispatchers.IO) {
            allFavouritesIDs = databaseDao.getAllFavouritesByEmail(email)
        }
    }

    fun checkIfFavourite(id: String) {
        _isFavourite.value = allFavouritesIDs.any { it.idMeal == id }
    }

    fun addFavourite(meal: Meals) {

        val favourite = ConvertData.mealToFavoriteInsert(meal, email)
        viewModelScope.launch(Dispatchers.IO) {
            databaseDao.addFavourite(favourite)
            withContext(Dispatchers.Main) {
                _toastMessage.value = "added to favourite"
            }
        }
    }

    fun removeFavourite(meal: Meals) {
        val favourite = ConvertData.mealToFavoriteDelete(meal, email, MealData.getRecordID())
        viewModelScope.launch(Dispatchers.IO) {
            databaseDao.deleteFavourite(favourite)
            withContext(Dispatchers.Main) {
                _toastMessage.value = "removed from favourite"
            }
        }
    }
}