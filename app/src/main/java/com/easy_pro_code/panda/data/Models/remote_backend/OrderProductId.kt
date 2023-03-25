package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class OrderProductId(

	@field:SerializedName("Variant")
	val variant: List<String?>? = null,

	@field:SerializedName("multiImg")
	val multiImg: List<String?>? = null,

	@field:SerializedName("Category")
	val category: String? = null,

	@field:SerializedName("Price")
	val price: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("Brands")
	val brands: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("Image")
	val image: String? = null
) : Parcelable
