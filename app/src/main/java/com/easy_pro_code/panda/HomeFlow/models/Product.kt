package com.easy_pro_code.panda.HomeFlow.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val category: String? = null,
    val price: String? = null,
    val title: String? = null,
    val id: String? = null,
    val image: String? = null,
    val rate:Int?=null
): Parcelable