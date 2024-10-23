package com.example.slidingroot.classes

import com.example.slidingroot.domains.Meals

object MealData {
    private var meal = Meals()
    private lateinit var items : Items
    private var ingredients = listOf<String>()
    private var measures = listOf<String>()
    private var recordID = 0


    fun setMeal(meal : Meals){
        this.meal = meal
        setItems()
    }

    fun getMeal() : Meals {
        return meal
    }

    fun setRecordID(id : Int){
        recordID = id
    }

    private fun setItems() {
        val allIngredients = listOf(
            meal.strIngredient1, meal.strIngredient2, meal.strIngredient3
            , meal.strIngredient4, meal.strIngredient5, meal.strIngredient6
            , meal.strIngredient7, meal.strIngredient8, meal.strIngredient9
            , meal.strIngredient10, meal.strIngredient11, meal.strIngredient12
            , meal.strIngredient13, meal.strIngredient14, meal.strIngredient15
            , meal.strIngredient16, meal.strIngredient17, meal.strIngredient18
            , meal.strIngredient19, meal.strIngredient20
        )

        val allMeasures = listOf(
            meal.strMeasure1, meal.strMeasure2, meal.strMeasure3,
            meal.strMeasure4, meal.strMeasure5, meal.strMeasure6,
            meal.strMeasure7, meal.strMeasure8, meal.strMeasure9,
            meal.strMeasure10, meal.strMeasure11, meal.strMeasure12,
            meal.strMeasure13, meal.strMeasure14, meal.strMeasure15,
            meal.strMeasure16, meal.strMeasure17, meal.strMeasure18,
            meal.strMeasure19, meal.strMeasure20
        )

        ingredients = allIngredients.filterNotNull().filter{ it.isNotBlank() }
        measures = allMeasures.filterNotNull().filter{ it.isNotBlank() }

        items = Items(ingredients,measures)
    }

    fun getName() : String{
        return meal.strMeal.toString()
    }

    fun getCategory() : String{
        return meal.strCategory.toString()
    }

    fun getCountry() : String{
        return meal.strArea.toString()
    }

    fun getImageURL() : String{
        return meal.strMealThumb.toString()
    }

    fun getVideoURL() : String{
        return meal.strYoutube.toString()
    }

    fun getDrink(): String {
        return meal.strDrinkAlternate ?: "No Alternate Drink"
    }

    fun getSteps(): String {
        return meal.strInstructions.toString()
    }

    fun getItems() : Items {
        return items
    }

    fun getID() : String {
        return meal.idMeal.toString()
    }

    fun getRecordID() : Int{
        return recordID
    }
}