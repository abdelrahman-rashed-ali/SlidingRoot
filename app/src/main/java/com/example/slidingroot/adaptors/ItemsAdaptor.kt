package com.example.slidingroot.adaptors

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.slidingroot.R
import com.example.slidingroot.classes.Items

class ItemsAdaptor(private val items : Items) : RecyclerView.Adapter<ItemsAdaptor.ItemsView>() {

    class ItemsView(val view : View) : RecyclerView.ViewHolder(view){
        val imageView: ImageView = view.findViewById(R.id.ingredient_image)
        val textView: TextView = view.findViewById(R.id.ingredient_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsView {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.single_ingredient,parent,false)
        return ItemsView(inflate)
    }

    override fun getItemCount(): Int {
        return items.ingredients.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemsView, position: Int) {
        val imageUrl = "https://www.themealdb.com/images/ingredients/" + items.ingredients[position] + "-Small.png"
        Glide.with(holder.view.context).load(imageUrl).into(holder.imageView)
        holder.textView.text = items.ingredients[position] + "\n" + items.measures[position]
    }
}