package com.example.slidingroot.classes

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.slidingroot.R
import com.example.slidingroot.domains.Meals
import com.example.slidingroot.domains.ResponseMeals

object SuggestMealView {
    fun StartShowSuggestMeal(view : View , response : Meals) {
        val imageView = view.findViewById<ImageView>(R.id.sugges_meal)
        val mealName = view.findViewById<TextView>(R.id.sugg_meal_name)
        val imageFadeOut = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f)
        val textFadeOut = ObjectAnimator.ofFloat(mealName, "alpha", 1f, 0f)
        imageFadeOut.duration = 500
        textFadeOut.duration = 500

        val imageFadeIn = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f)
        val textFadeIn = ObjectAnimator.ofFloat(mealName, "alpha", 0f, 1f)
        imageFadeIn.duration = 500
        textFadeIn.duration = 500

        textFadeOut.start()
        imageFadeOut.start()

        imageFadeOut.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(p0: Animator) {
                Glide.with(view.context).load(response.strMealThumb).into(imageView)
                mealName.text = response.strMeal
                textFadeIn.start()
                imageFadeIn.start()
            }

            override fun onAnimationStart(animation: Animator, isReverse: Boolean) {}
            override fun onAnimationStart(p0: Animator) {}
            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}
        })
    }
}