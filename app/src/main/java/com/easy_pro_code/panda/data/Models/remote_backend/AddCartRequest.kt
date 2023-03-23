package com.easy_pro_code.panda.data.Models.remote_backend

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddCartRequest(

	@field:SerializedName("Items")
	val items: List<ProductItemsItemRequest?>? = null,

	@field:SerializedName("userId")
	val userId: String? = null
) : Parcelable

@Parcelize
data class ProductItemsItemRequest(

	@field:SerializedName("productId")
	val productId: String? = null,

	@field:SerializedName("Number")
	val number: Int? = null
) : Parcelable
