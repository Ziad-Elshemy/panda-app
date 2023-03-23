package com.easy_pro_code.panda.data.local_database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.easy_pro_code.panda.data.Models.local_database.WishProduct

@Dao
interface WishListDao {
    @Query("SELECT * From WishProduct")
    fun getAll():LiveData<List<WishProduct>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWishProduct(product: WishProduct)
    @Delete
    suspend fun removeWishProduct(product: WishProduct)
}