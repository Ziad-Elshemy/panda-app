package com.easy_pro_code.panda.HomeFlow.models

import com.easy_pro_code.panda.data.Models.local_database.WishProduct
import com.easy_pro_code.panda.data.Models.remote_backend.*
import com.google.gson.Gson

fun List<ProductsItem>?.fromProductToProduct(): List<Product>? = this?.map {
        Product(
            category = it.category,
            price = it.price,
            title = it.title,
            id=it.id,
            image = it.image,
            rate = it.rate?:5,
            productVariant = ProductVariant(it.variant),
            multiImg = ProductMultiImage(it.multiImg),
            prands = it.prands
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
            rate = product?.rate?:5,
            productVariant = ProductVariant(product?.variant),
            multiImg = ProductMultiImage(product?.multiImg),
            prands = product?.prands
        ),
        it.newPrice.toString()
    )
}

fun List<ProductsItem>?.fromPhoneToProduct(): List<Phone>? = this?.map {
    Phone(
        Product(
            category = it?.category,
            price = it?.price,
            title = it?.title,
            id=it?.id,
            image = it?.image,
            rate = it?.rate?:5,
            productVariant = ProductVariant(it?.variant),
            multiImg = ProductMultiImage(it?.multiImg),
            prands = it?.prands
        )
    )
}



fun Product.fromProductItemToWishProduct():WishProduct=
    WishProduct(
        productId = this.id.toString(),
        category=this.category,
        price=this.price,
        title=this.title,
        image=this.image,
        rate=this.rate?:5,
        variant = Gson().toJson(this.productVariant,ProductVariant::class.java),
        multiImg = Gson().toJson(this.multiImg,ProductMultiImage::class.java),
        prands = this.prands
    )


fun List<WishProduct>?.fromWishProductToProduct(): List<Product>? = this?.map {
    Product(
        category = it.category,
        price = it.price,
        title = it.title,
        id=it.productId,
        image = it.image,
        rate = it.rate?:5,
        productVariant = Gson().fromJson(it.variant,ProductVariant::class.java),
        multiImg = Gson().fromJson(it.multiImg,ProductMultiImage::class.java),
        prands = it.prands
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

fun List<CategoryItem>?.categoryItemToAllCategoryName():List<String>? =
    this?.map {
        it.title?:""
    }

