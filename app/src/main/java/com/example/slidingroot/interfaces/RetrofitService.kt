package com.example.slidingroot.interfaces

import com.example.slidingroot.domains.ResponseMeals
import com.example.slidingroot.domains.allCategories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("random.php")
    suspend fun getRandomMeal() : Response<ResponseMeals>

    @GET("categories.php")
    suspend fun getCategories() : Response<allCategories>

    @GET("filter.php")
    suspend fun getByCategory(@Query("c") category: String): Response<ResponseMeals>

    @GET("search.php")
    suspend fun getBySearch(@Query("s") search: String): Response<ResponseMeals>

    @GET("lookup.php")
    suspend fun getByID(@Query("i") category: String): Response<ResponseMeals>

}