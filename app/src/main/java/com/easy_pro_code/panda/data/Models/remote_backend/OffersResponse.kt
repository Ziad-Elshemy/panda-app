package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class OffersResponse(

	@field:SerializedName("offers")
	val offers: List<OffersItem>? = null
) : Parcelable

@Parcelize
data class OffersItem(

	@field:SerializedName("productId")
	val productId: ProductsItem? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("newPrice")
	val newPrice: Int? = null,

	@field:SerializedName("createDate")
	val createDate: String? = null
) : Parcelable
