package com.example.slidingroot.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.slidingroot.R
import com.example.slidingroot.ShowMeal
import com.example.slidingroot.ViewModel.ShowMealViewModel
import com.example.slidingroot.classes.MealData
import com.google.firebase.auth.FirebaseAuth

class ShowMealStatic : Fragment() {

    private lateinit var saveView: View
    private var guest = false
    private val viewModel: ShowMealViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (requireContext().getSharedPreferences("Guest", Context.MODE_PRIVATE).getBoolean("guest",false)){
            guest = true
            viewModel.init("None")
        }else{
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser!!
            viewModel.init(user.email.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_meals_static, container, false)
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveView = view

        Glide.with(view.context).load(MealData.getImageURL()).into(view.findViewById(R.id.show_meal_image))
        view.findViewById<TextView>(R.id.show_meal_name).text = MealData.getName()

        view.findViewById<Button>(R.id.back_show_movie).setOnClickListener {
            activity?.finish()
        }

        (activity as ShowMeal).onDetailsSelect()
        view.findViewById<Button>(R.id.button2).isSelected = true

        val buttons = listOf(
            view.findViewById<Button>(R.id.button2),
            view.findViewById(R.id.button3),
            view.findViewById(R.id.button4),
            view.findViewById(R.id.button5)
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                buttons.forEach { it.isSelected = false }
                button.isSelected = true
                when (button.id) {
                    R.id.button2 -> (activity as ShowMeal).onDetailsSelect()
                    R.id.button3 -> (activity as ShowMeal).onItemsSelect()
                    R.id.button4 -> (activity as ShowMeal).onStepsSelect()
                    R.id.button5 -> (activity as ShowMeal).onVideoSelect()
                }
            }
        }

        viewModel.isFavourite.observe(viewLifecycleOwner) { isFav ->
            view.findViewById<CheckBox>(R.id.isFav).isChecked = isFav
            view.findViewById<CheckBox>(R.id.isFav).setOnCheckedChangeListener { _, isChecked ->
                if(!guest){
                    if (isChecked) {
                        viewModel.addFavourite(MealData.getMeal())
                    } else {
                        viewModel.removeFavourite(MealData.getMeal())
                    }
                }else{
                    (activity as ShowMeal).showCustomDialog("Please Log in to Continue")
                    view.findViewById<CheckBox>(R.id.isFav).isChecked = false
                }
            }
        }

        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        viewModel.checkIfFavourite(MealData.getID())
    }
}
