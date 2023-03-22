package com.easy_pro_code.panda.data.local_database.app_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.easy_pro_code.panda.data.Models.local_database.WishProduct
import com.easy_pro_code.panda.data.local_database.WishListDao

@Database(entities = [WishProduct::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wishListDao(): WishListDao
    companion object{
        var database:AppDatabase?=null
        fun getInstance():AppDatabase{
            return database!!
        }

        fun initialize(context: Context) {
            database=Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "Panda"
            ).build()
        }

    }
}