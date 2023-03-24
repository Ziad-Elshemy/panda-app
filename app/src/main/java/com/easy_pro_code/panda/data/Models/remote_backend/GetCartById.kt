package com.easy_pro_code.panda.data.Models.remote_backend

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetCartById(

	@field:SerializedName("carts")
	val carts: List<CartsItem?>? = null
) : Parcelable
//
//@Parcelize
//data class ProductId(
//
//	@field:SerializedName("Variant")
//	val variant: List<String?>? = null,
//
//	@field:SerializedName("multiImg")
//	val multiImg: List<String?>? = null,
//
//	@field:SerializedName("Category")
//	val category: String? = null,
//
//	@field:SerializedName("Price")
//	val price: String? = null,
//
//	@field:SerializedName("__v")
//	val V: Int? = null,
//
//	@field:SerializedName("Title")
//	val title: String? = null,
//
//	@field:SerializedName("Prands")
//	val prands: String? = null,
//
//	@field:SerializedName("_id")
//	val id: String? = null,
//
//	@field:SerializedName("Image")
//	val image: String? = null
//) : Parcelable

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
