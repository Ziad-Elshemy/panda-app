package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AddCartRequest(

	@field:SerializedName("Items")
	val items: List<ItemsItemResponse?>? = null,

	@field:SerializedName("userId")
	val userId: String? = null
) : Parcelable

@Parcelize
data class ItemsItem(

	@field:SerializedName("productId")
	val productId: String? = null,

	@field:SerializedName("Number")
	val number: Int? = null
) : Parcelable
