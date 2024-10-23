package com.example.slidingroot.adaptors

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.slidingroot.R
import com.example.slidingroot.SearchActivity
import com.example.slidingroot.domains.Meals
import com.example.slidingroot.fragments.HomeFragment

class MealsAdaptor(private val data : List<Meals> , val controller : HomeFragment?=null , val activity : SearchActivity?=null) : RecyclerView.Adapter<MealsAdaptor.MealsView>() {

    inner class MealsView(val view : View) : RecyclerView.ViewHolder(view){
        val mealImage = view.findViewById<ImageView>(R.id.small_meal_image)!!
        val mealName = view.findViewById<TextView>(R.id.small_meal_name)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsView {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.single_meal_small,parent,false)
        return MealsView(inflate)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MealsView, position: Int) {
        Glide.with(holder.view.context).load(data[position].strMealThumb).into(holder.mealImage)
        holder.mealName.text = data[position].strMeal.toString()
        if (data[position].strYoutube.isNullOrEmpty()){

            holder.view.setOnClickListener{
                controller?.getByID(data[position].idMeal.toString())
            }

        } else {
            holder.view.setOnClickListener{
                activity?.onMealClick(data[position])
            }

        }

    }
}