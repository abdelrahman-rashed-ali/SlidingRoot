package com.example.slidingroot.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slidingroot.MainActivity
import com.example.slidingroot.R
import com.example.slidingroot.SearchActivity
import com.example.slidingroot.ShowMeal
import com.example.slidingroot.ViewModel.MainActivityViewModel
import com.example.slidingroot.ViewModel.MainActivityViewModelFactory
import com.example.slidingroot.adaptors.CategoryAdaptor
import com.example.slidingroot.adaptors.MealsAdaptor
import com.example.slidingroot.classes.MealData
import com.example.slidingroot.classes.SuggestMealView
import com.example.slidingroot.classes.retrofit
import com.example.slidingroot.domains.Categories
import com.example.slidingroot.classes.checks
import com.example.slidingroot.database.LocalDatabase
import com.example.slidingroot.domains.Meals
import com.example.slidingroot.interfaces.HomeFragmentListener

class HomeFragment : Fragment() , HomeFragmentListener {
    private lateinit var menuButton : Button
    private lateinit var searchButton : Button
    private lateinit var saveView : View
    private var lastMeal : Meals? = null
    private lateinit var randomMealContainer : CardView
    private var loadPage = false
    private var clicked = false
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SetTextI18n", "CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = LocalDatabase.getInstance(requireContext()).databaseDao()
        val retrofitService = retrofit

        viewModelFactory = MainActivityViewModelFactory(dao, retrofitService)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]

        saveView = view

        menuButton = view.findViewById(R.id.menu_button)
        menuButton.setOnClickListener {
            val getActivity = activity as MainActivity
            getActivity.clickEventSlide()
        }

        searchButton = view.findViewById(R.id.search_button)
        searchButton.setOnClickListener {
            val intent = Intent(view.context,SearchActivity::class.java)
            view.context.startActivity(intent)
        }

        setupObservers()
        randomMealContainer = view.findViewById(R.id.container_suggest_meal)

    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()

        // Observe meal
        viewModel.meal.observe(viewLifecycleOwner) { meal ->
            if (meal != null) {
                SuggestMealView.StartShowSuggestMeal(saveView, meal)
                setupRandomMealClickListener(meal)
                lastMeal = meal
            } else {
                lastMeal?.let{ setupRandomMealClickListener(it) } ?: run {showNoInternetUI()}
            }
        }

        // Trigger data fetching
        if (checks.isNetworkAvailable(requireContext())) {
            viewModel.fetchRandomMeal()
            if(!loadPage)
                viewModel.fetchCategories()
        } else {
            showNoInternetUI()
        }
    }

    private fun setupObservers() {
        // Observe categories
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            if (categories.isNotEmpty()) {
                loadPage = true
                setupCategoriesRecyclerView(categories)
            }
        }

        // Observe meals by category
        viewModel.mealsByCategory.observe(viewLifecycleOwner) { meals ->
            setupMealsRecyclerView(meals)
        }

        // Observe network availability
        viewModel.networkAvailable.observe(viewLifecycleOwner) { isAvailable ->
            if (!isAvailable) showNoInternetUI()
        }

        viewModel.mealsByID.observe(viewLifecycleOwner) { meal ->
            if (clicked)
                onMealClick(meal)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showNoInternetUI() {
        val imageView = saveView.findViewById<ImageView>(R.id.sugges_meal)
        val mealName = saveView.findViewById<TextView>(R.id.sugg_meal_name)
        randomMealContainer.setOnClickListener {}
        imageView.setImageResource(R.drawable.no_connection_icon)
        mealName.text = "Please Connect to Internet"
    }

    private fun setupRandomMealClickListener(meal: Meals) {
        randomMealContainer.setOnClickListener {
            MealData.setMeal(meal)
            val intent = Intent(saveView.context, ShowMeal::class.java)
            saveView.context.startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadPage = false
    }

    private fun setupCategoriesRecyclerView(categories: List<Categories>) {
        val rc = saveView.findViewById<RecyclerView>(R.id.category_rc)
        rc.layoutManager = LinearLayoutManager(saveView.context, RecyclerView.HORIZONTAL, false)
        rc.adapter = CategoryAdaptor(categories, this)
    }

    @SuppressLint("SetTextI18n")
    private fun setupMealsRecyclerView(meals: List<Meals>) {
        val rcMeals = saveView.findViewById<RecyclerView>(R.id.small_meals_rc_home)
        rcMeals.layoutManager = LinearLayoutManager(saveView.context, RecyclerView.HORIZONTAL, false)
        rcMeals.adapter = MealsAdaptor(meals, this)
        requireView().findViewById<TextView>(R.id.meals_category_name).text = "Meals In Category"
    }

    fun getByID(id: String) {
        clicked = true
        viewModel.fetchMealByID(id)
    }

    override fun onCategoryClick(category: String) {
        viewModel.fetchMealsByCategory(category)
    }

    override fun onMealClick(meal: Meals) {
        clicked = false
        MealData.setMeal(meal)
        val intent = Intent(requireContext(), ShowMeal::class.java)
        startActivity(intent)
    }

}
