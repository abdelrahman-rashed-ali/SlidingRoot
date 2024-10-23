package com.example.slidingroot.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.slidingroot.R
import com.example.slidingroot.classes.ConvertData
import com.example.slidingroot.domains.Favourite
import com.example.slidingroot.fragments.FavouriteFragment

class FavouritesAdapter(private val data : List<Favourite>, private val controller : FavouriteFragment ) : RecyclerView.Adapter<FavouritesAdapter.FavouritesView>() {

    class FavouritesView(val view : View) : RecyclerView.ViewHolder(view){
        val mealImage = view.findViewById<ImageView>(R.id.small_meal_image)!!
        val mealName = view.findViewById<TextView>(R.id.small_meal_name)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesView {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.single_meal_small,parent,false)
        return FavouritesView(inflate)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FavouritesView, position: Int) {
        Glide.with(holder.view.context).load(data[position].strMealThumb).into(holder.mealImage)
        holder.mealName.text = data[position].strMeal.toString()
        holder.view.setOnClickListener{
            controller.onFavouriteClick(ConvertData.favouriteToMeal(data[position]) , data[position].recordID)
        }
    }
}