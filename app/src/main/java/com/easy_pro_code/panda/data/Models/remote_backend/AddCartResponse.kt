package com.easy_pro_code.panda.data.Models.remote_backend

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddCartResponse(

	@field:SerializedName("cart")
	val cart: Cart? = null
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
data class Cart(

    @field:SerializedName("__v")
	val V: Int? = null,

    @field:SerializedName("Items")
	val items: List<ProductItemsItemRequest?>? = null,

    @field:SerializedName("_id")
	val id: String? = null,

    @field:SerializedName("userId")
	val userId: String? = null,

    @field:SerializedName("Date")
	val date: String? = null
) : Parcelable
