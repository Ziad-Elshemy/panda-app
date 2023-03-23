package com.easy_pro_code.panda.HomeFlow.models

import com.easy_pro_code.panda.HomeFlow.view_model.CartsItem

fun List<CartsItem?>?.toCart() : List<CartModel>? =this?.map {
    CartModel(
       cartId = it?.id
    )
}