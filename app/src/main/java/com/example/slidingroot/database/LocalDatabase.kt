package com.example.slidingroot.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.slidingroot.domains.Favourite
import com.example.slidingroot.domains.Plan
import com.example.slidingroot.interfaces.DatabaseDao


@Database(entities = [Favourite::class], version = 6)
abstract class LocalDatabase :  RoomDatabase(){
    abstract fun databaseDao(): DatabaseDao
        companion object {
            @Volatile
            private var INSTANCE: LocalDatabase? = null
            fun getInstance(context: Context): LocalDatabase {
                return INSTANCE?: synchronized (this) {
                    val tempInstance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDatabase::class.java, "local_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = tempInstance
                    tempInstance
                }
        }
    }
}
