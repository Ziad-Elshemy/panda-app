package com.easy_pro_code.panda.HomeFlow.models

import com.easy_pro_code.panda.data.Models.remote_backend.ProductsItem

fun List<ProductsItem>?.toProduct(): List<Product>? = this?.map {
        Product(
            category = it.category,
            price = it.price,
            title = it.title,
            id=it.id,
            image = it.image
        )
    }