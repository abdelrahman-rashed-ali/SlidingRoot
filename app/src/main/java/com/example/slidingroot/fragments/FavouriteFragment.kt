package com.example.slidingroot.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slidingroot.MainActivity
import com.example.slidingroot.R
import com.example.slidingroot.ShowMeal
import com.example.slidingroot.ViewModel.MainActivityViewModel
import com.example.slidingroot.ViewModel.MainActivityViewModelFactory
import com.example.slidingroot.adaptors.FavouritesAdapter
import com.example.slidingroot.classes.MealData
import com.example.slidingroot.classes.retrofit
import com.example.slidingroot.database.LocalDatabase
import com.example.slidingroot.domains.Meals
import com.example.slidingroot.interfaces.DatabaseDao
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class FavouriteFragment : Fragment() {
    private lateinit var localDatabase : DatabaseDao
    private lateinit var saveView : View
    private lateinit var email : String
    private lateinit var rcFav : RecyclerView
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = LocalDatabase.getInstance(requireContext()).databaseDao()
        val retrofitService = retrofit

        viewModelFactory = MainActivityViewModelFactory(dao, retrofitService)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]

        if (!(activity as MainActivity).guest){
            val auth = Firebase.auth
            val user = auth.currentUser!!
            email = user.email.toString()
        }



        saveView = view
        localDatabase = LocalDatabase.getInstance(view.context).databaseDao()
        rcFav = view.findViewById(R.id.rc_fav)
        rcFav.layoutManager = GridLayoutManager(view.context, 2,GridLayoutManager.VERTICAL,false)

        val menuButton = view.findViewById<Button>(R.id.menu_button_fav)
        menuButton.setOnClickListener {
            val getActivity = activity as MainActivity
            getActivity.clickEventSlide()
        }

        // Observe LiveData from ViewModel
        viewModel.favourites.observe(viewLifecycleOwner) { allFav ->
            if (allFav.isNotEmpty()){
                rcFav.adapter = FavouritesAdapter(allFav, this@FavouriteFragment)
                requireView().findViewById<TextView>(R.id.no_favourites).text = ""
            } else {
                requireView().findViewById<TextView>(R.id.no_favourites).text = "No Favourites"
            }
        }

    }


    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        if ((activity as MainActivity).guest){
            requireView().findViewById<TextView>(R.id.no_favourites).text = "Please Log in to add Favourites"
        } else {
            viewModel.fetchFavourites(email)
        }
    }


    fun onFavouriteClick(meal: Meals, id: Int) {
        MealData.setMeal(meal)
        MealData.setRecordID(id)
        val intent = Intent(saveView.context, ShowMeal::class.java)
        saveView.context.startActivity(intent)
    }

}