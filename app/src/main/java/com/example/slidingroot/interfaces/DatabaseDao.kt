package com.example.slidingroot.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.slidingroot.domains.Favourite
import com.example.slidingroot.domains.Meals

@Dao
interface DatabaseDao {

    @Query("SELECT * FROM Favourite WHERE Email = :e")
    suspend fun getAllFavouritesByEmail(e : String): List<Favourite>


    @Insert
    suspend fun addFavourite(obj : Favourite) : Long

    @Delete
    suspend fun deleteFavourite(obj : Favourite) : Int
}