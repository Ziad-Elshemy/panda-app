package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AddCartRequest(

	@field:SerializedName("cart")
	val cart: Cart? = null
) : Parcelable

@Parcelize
data class Cart(

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

@Parcelize
data class ItemsItem(

	@field:SerializedName("productId")
	val productId: String? = null,

	@field:SerializedName("Number")
	val number: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null
) : Parcelable
