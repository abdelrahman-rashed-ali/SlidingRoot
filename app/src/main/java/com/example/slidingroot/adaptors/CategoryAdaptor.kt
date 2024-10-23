package com.example.slidingroot.adaptors

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.slidingroot.R
import com.example.slidingroot.domains.Categories
import com.example.slidingroot.fragments.HomeFragment

class CategoryAdaptor(private val data : List<Categories> ,private val controller : HomeFragment) : RecyclerView.Adapter<CategoryAdaptor.CategoryView>() {
    private var selectedPosition = 0
    class CategoryView(val view : View) : RecyclerView.ViewHolder(view){
        val categoryImage = view.findViewById<ImageView>(R.id.category_image)!!
        val categoryName = view.findViewById<TextView>(R.id.category_name)!!
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.container_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryView {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.single_category , parent,false)
        controller.onCategoryClick(data[selectedPosition].strCategory.toString())
        return CategoryView(inflate)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CategoryView, @SuppressLint("RecyclerView") position: Int) {
        Glide.with(holder.view.context).load(data[position].strCategoryThumb).into(holder.categoryImage)
        holder.categoryName.text = data[position].strCategory.toString().trimStart()
        holder.view.setOnClickListener{
            controller.onCategoryClick(data[position].strCategory.toString())
            selectedPosition = position
            notifyDataSetChanged()
        }

        if (selectedPosition == position) {
            holder.constraintLayout.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.selected_border)
        } else {
            holder.constraintLayout.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.head_bar_background) // Use your default background drawable
        }
    }
}