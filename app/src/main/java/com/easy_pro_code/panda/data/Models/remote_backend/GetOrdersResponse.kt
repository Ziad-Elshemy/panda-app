package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GetOrdersResponse(

	@field:SerializedName("orders")
	val orders: List<OrdersItem?>? = null
) : Parcelable

@Parcelize
data class OrderItemsItem(

	@field:SerializedName("productId")
	val productId: OrderProductId? = null,

	@field:SerializedName("Number")
	val number: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null
) : Parcelable


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
//	val v: Int? = null,
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
data class UserId(

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("Phone")
	val phone: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("userName")
	val userName: String? = null
) : Parcelable

@Parcelize
data class OrdersItem(

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: UserId? = null,

	@field:SerializedName("Cart")
	val cart: OrderCart? = null
) : Parcelable

@Parcelize
data class OrderCart(

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("Items")
	val items: List<OrderItemsItem?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("Date")
	val date: String? = null
) : Parcelable
