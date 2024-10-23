package com.example.slidingroot.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.slidingroot.classes.retrofit
import com.example.slidingroot.interfaces.DatabaseDao


class MainActivityViewModelFactory(
    private val dao: DatabaseDao,
    private val retrofit: retrofit
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(dao, retrofit) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
