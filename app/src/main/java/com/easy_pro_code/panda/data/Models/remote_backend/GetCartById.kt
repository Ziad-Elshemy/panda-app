package com.easy_pro_code.panda.data.Models.remote_backend

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetCartById(

	@field:SerializedName("carts")
	val carts: List<CartsItem?>? = null
) : Parcelable

@Parcelize
data class CartsItem(

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("Items")
	val items: List<ProductItemsItem?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("Date")
	val date: String? = null
) : Parcelable

@Parcelize
data class ProductItemsItem(

	@field:SerializedName("productId")
	val productId: ProductsItem? = null,

	@field:SerializedName("Number")
	val number: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null
) : Parcelable
