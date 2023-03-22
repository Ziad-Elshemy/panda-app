package com.easy_pro_code.panda.HomeFlow.models

import com.easy_pro_code.panda.data.Models.local_database.WishProduct
import com.easy_pro_code.panda.data.Models.remote_backend.CategoryItem
import com.easy_pro_code.panda.data.Models.remote_backend.OffersItem
import com.easy_pro_code.panda.data.Models.remote_backend.OffersResponse
import com.easy_pro_code.panda.data.Models.remote_backend.ProductsItem

fun List<ProductsItem>?.fromProductToProduct(): List<Product>? = this?.map {
        Product(
            category = it.category,
            price = it.price,
            title = it.title,
            id=it.id,
            image = it.image,
            rate = it.rate
        )
}


fun List<OffersItem>?.fromOfferToProduct(): List<Offer>? = this?.map {
    val product=it.productId
    Offer(
        Product(
            category = product?.category,
            price = product?.price,
            title = product?.title,
            id=product?.id,
            image = product?.image,
            rate = product?.rate
        ),
        it.newPrice.toString()
    )
}

fun Product.fromProductItemToWishProduct():WishProduct=
    WishProduct(
        productId = this.id.toString(),
        category=this.category,
        price=this.price,
        title=this.title,
        image=this.image,
        rate=this.rate
    )


fun List<WishProduct>?.fromWishProductToProduct(): List<Product>? = this?.map {
    Product(
        category = it.category,
        price = it.price,
        title = it.title,
        id=it.productId,
        image = it.image,
        rate = it.rate
    )
}

fun List<CategoryItem>?.categoryItemToMainCategoryName():List<String?>? {
    if (this==null) return null
    val categories= mutableListOf<String?>()
    this.map {
        if (it.main==null) categories.add(it.title)
    }
    return categories
}