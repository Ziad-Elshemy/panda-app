package com.easy_pro_code.panda.HomeFlow.models

data class MyCartModel(
    var price:Int ? = null  ,
    val title: String? = null,
    val userId: String? = null,
    val image: String? = null,
    var count:Int ? = null,
    val productId: String?=null,
    val cartId:String?=null,
    val date: String?=null

)