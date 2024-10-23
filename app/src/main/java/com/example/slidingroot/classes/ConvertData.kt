package com.example.slidingroot.classes

import com.example.slidingroot.domains.Favourite
import com.example.slidingroot.domains.Meals

object ConvertData {

    fun mealToFavoriteInsert(meal: Meals, email: String): Favourite {
        return copyCommonFields(meal, email)
    }

    fun mealToFavoriteDelete(meal: Meals, email: String, id: Int): Favourite {
        return copyCommonFields(meal, email, id)
    }

    private fun copyCommonFields(meal: Meals, email: String? = null, id: Int? = null): Favourite {
        return Favourite(
            recordID = id ?: 0,
            idMeal = meal.idMeal,
            Email = email ?: "",
            strMeal = meal.strMeal,
            strDrinkAlternate = meal.strDrinkAlternate,
            strCategory = meal.strCategory,
            strArea = meal.strArea,
            strInstructions = meal.strInstructions,
            strMealThumb = meal.strMealThumb,
            strTags = meal.strTags,
            strYoutube = meal.strYoutube,
            strIngredient1 = meal.strIngredient1,
            strIngredient2 = meal.strIngredient2,
            strIngredient3 = meal.strIngredient3,
            strIngredient4 = meal.strIngredient4,
            strIngredient5 = meal.strIngredient5,
            strIngredient6 = meal.strIngredient6,
            strIngredient7 = meal.strIngredient7,
            strIngredient8 = meal.strIngredient8,
            strIngredient9 = meal.strIngredient9,
            strIngredient10 = meal.strIngredient10,
            strIngredient11 = meal.strIngredient11,
            strIngredient12 = meal.strIngredient12,
            strIngredient13 = meal.strIngredient13,
            strIngredient14 = meal.strIngredient14,
            strIngredient15 = meal.strIngredient15,
            strIngredient16 = meal.strIngredient16,
            strIngredient17 = meal.strIngredient17,
            strIngredient18 = meal.strIngredient18,
            strIngredient19 = meal.strIngredient19,
            strIngredient20 = meal.strIngredient20,
            strMeasure1 = meal.strMeasure1,
            strMeasure2 = meal.strMeasure2,
            strMeasure3 = meal.strMeasure3,
            strMeasure4 = meal.strMeasure4,
            strMeasure5 = meal.strMeasure5,
            strMeasure6 = meal.strMeasure6,
            strMeasure7 = meal.strMeasure7,
            strMeasure8 = meal.strMeasure8,
            strMeasure9 = meal.strMeasure9,
            strMeasure10 = meal.strMeasure10,
            strMeasure11 = meal.strMeasure11,
            strMeasure12 = meal.strMeasure12,
            strMeasure13 = meal.strMeasure13,
            strMeasure14 = meal.strMeasure14,
            strMeasure15 = meal.strMeasure15,
            strMeasure16 = meal.strMeasure16,
            strMeasure17 = meal.strMeasure17,
            strMeasure18 = meal.strMeasure18,
            strMeasure19 = meal.strMeasure19,
            strMeasure20 = meal.strMeasure20,
            strSource = meal.strSource,
            strImageSource = meal.strImageSource,
            strCreativeCommonsConfirmed = meal.strCreativeCommonsConfirmed,
            dateModified = meal.dateModified
        )
    }

    fun favouriteToMeal(favourite: Favourite): Meals {
        return Meals(
            idMeal = favourite.idMeal,
            strMeal = favourite.strMeal,
            strDrinkAlternate = favourite.strDrinkAlternate,
            strCategory = favourite.strCategory,
            strArea = favourite.strArea,
            strInstructions = favourite.strInstructions,
            strMealThumb = favourite.strMealThumb,
            strTags = favourite.strTags,
            strYoutube = favourite.strYoutube,
            strIngredient1 = favourite.strIngredient1,
            strIngredient2 = favourite.strIngredient2,
            strIngredient3 = favourite.strIngredient3,
            strIngredient4 = favourite.strIngredient4,
            strIngredient5 = favourite.strIngredient5,
            strIngredient6 = favourite.strIngredient6,
            strIngredient7 = favourite.strIngredient7,
            strIngredient8 = favourite.strIngredient8,
            strIngredient9 = favourite.strIngredient9,
            strIngredient10 = favourite.strIngredient10,
            strIngredient11 = favourite.strIngredient11,
            strIngredient12 = favourite.strIngredient12,
            strIngredient13 = favourite.strIngredient13,
            strIngredient14 = favourite.strIngredient14,
            strIngredient15 = favourite.strIngredient15,
            strIngredient16 = favourite.strIngredient16,
            strIngredient17 = favourite.strIngredient17,
            strIngredient18 = favourite.strIngredient18,
            strIngredient19 = favourite.strIngredient19,
            strIngredient20 = favourite.strIngredient20,
            strMeasure1 = favourite.strMeasure1,
            strMeasure2 = favourite.strMeasure2,
            strMeasure3 = favourite.strMeasure3,
            strMeasure4 = favourite.strMeasure4,
            strMeasure5 = favourite.strMeasure5,
            strMeasure6 = favourite.strMeasure6,
            strMeasure7 = favourite.strMeasure7,
            strMeasure8 = favourite.strMeasure8,
            strMeasure9 = favourite.strMeasure9,
            strMeasure10 = favourite.strMeasure10,
            strMeasure11 = favourite.strMeasure11,
            strMeasure12 = favourite.strMeasure12,
            strMeasure13 = favourite.strMeasure13,
            strMeasure14 = favourite.strMeasure14,
            strMeasure15 = favourite.strMeasure15,
            strMeasure16 = favourite.strMeasure16,
            strMeasure17 = favourite.strMeasure17,
            strMeasure18 = favourite.strMeasure18,
            strMeasure19 = favourite.strMeasure19,
            strMeasure20 = favourite.strMeasure20,
            strSource = favourite.strSource,
            strImageSource = favourite.strImageSource,
            strCreativeCommonsConfirmed = favourite.strCreativeCommonsConfirmed,
            dateModified = favourite.dateModified
        )
    }
}
