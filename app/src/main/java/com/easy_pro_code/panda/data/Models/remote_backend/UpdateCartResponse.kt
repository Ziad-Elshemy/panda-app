package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UpdateCartResponse(

	@field:SerializedName("update")
	val update: Update? = null
) : Parcelable

@Parcelize
data class Update(

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("Items")
	val items: List<UpdateItemsItemResponse?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("Date")
	val date: String? = null
) : Parcelable

@Parcelize
data class UpdateItemsItemResponse(

	@field:SerializedName("productId")
	val productId: String? = null,

	@field:SerializedName("Number")
	val number: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null
) : Parcelable
