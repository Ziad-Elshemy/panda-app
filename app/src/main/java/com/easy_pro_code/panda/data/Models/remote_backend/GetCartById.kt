package com.easy_pro_code.panda.HomeFlow.view_model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GetCartById(

	@field:SerializedName("carts")
	val carts: List<CartsItem?>? = null
) : Parcelable

@Parcelize
data class ItemsItem(

	@field:SerializedName("productId")
	val productId: String? = null,

	@field:SerializedName("Number")
	val number: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null
) : Parcelable

@Parcelize
data class CartsItem(

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("Items")
	val items: List<ItemsItem?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("Date")
	val date: String? = null
) : Parcelable
