package com.example.slidingroot

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slidingroot.adaptors.MealsAdaptor
import com.example.slidingroot.classes.MealData
import com.example.slidingroot.classes.activity_settings
import com.example.slidingroot.classes.checks
import com.example.slidingroot.classes.retrofit
import com.example.slidingroot.domains.Meals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var searchView: SearchView
    private lateinit var rcResults: RecyclerView
    private var searchJob: Job? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_activity)
        activity_settings.apply_text(window, resources)
        activity_settings.apply_size(findViewById(R.id.main))

        rcResults = findViewById(R.id.rc_results)
        rcResults.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL,false)

        searchView = findViewById(R.id.search_view)

        backButton = findViewById(R.id.back_search_view)
        backButton.setOnClickListener {
            finish()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            @SuppressLint("SetTextI18n")
            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()

                if (checks.isNetworkAvailable(this@SearchActivity)) {
                    findViewById<TextView>(R.id.no_internet_search).text = ""
                    searchJob = lifecycleScope.launch(Dispatchers.IO) {
                        var response: List<Meals>?
                        try {
                            val result = retrofit.Service.getBySearch(newText.toString()).body()
                            response = result?.meals
                        } catch (ex: Exception) {
                            response = emptyList()
                        }
                        withContext(Dispatchers.Main) {
                            rcResults.adapter = MealsAdaptor(response ?: emptyList(), null, this@SearchActivity)
                        }
                    }
                } else {
                    findViewById<TextView>(R.id.no_internet_search).text = "No Internet"
                }
                return true
            }
        })
    }

    override fun onStart() {
        super.onStart()
        searchView.onActionViewExpanded()
        showKeyboard()
    }

    private fun showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT)
    }


    override fun dispatchKeyEvent(event: android.view.KeyEvent): Boolean {
        if (event.keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    fun onMealClick(meal: Meals) {
        MealData.setMeal(meal)
        val intent = Intent(this,ShowMeal::class.java)
        startActivity(intent)
    }
}
