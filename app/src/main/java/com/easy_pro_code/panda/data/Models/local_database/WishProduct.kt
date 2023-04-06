package com.easy_pro_code.panda.data.Models.local_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easy_pro_code.panda.data.Models.remote_backend.VariantItem

@Entity
data class WishProduct(
    @PrimaryKey val productId: String,
    @ColumnInfo(name = "category") val category: String? = null,
    @ColumnInfo(name = "price") val price: String? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "image") val image: String? = null,
    @ColumnInfo(name = "rate") val rate:Int?=null,
    @ColumnInfo(name = "variant") val variant:String,
    @ColumnInfo(name="multiImg") val multiImg:String,
    @ColumnInfo("prands") val prands:String?=null
)
