package com.easy_pro_code.panda.data.Models.local_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WishProduct(
    @PrimaryKey val productId: String? = null,
    @ColumnInfo(name = "category") val category: String? = null,
    @ColumnInfo(name = "price") val price: String? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "image") val image: String? = null,
    @ColumnInfo(name = "rate") val rate:Int?=null
)
