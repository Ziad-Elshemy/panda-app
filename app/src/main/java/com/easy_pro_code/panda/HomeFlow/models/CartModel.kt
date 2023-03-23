package com.easy_pro_code.panda.HomeFlow.models

import android.provider.ContactsContract.Data

data class CartModel(
    val category: String? = null,
    val price: String? = null,
    val title: String? = null,
    val id: String? = null,
    val image: String? = null,
    val count:Int ? = null,
    val productId: String?=null,
    val cartId :String?=null,
    val data: String?=null

)