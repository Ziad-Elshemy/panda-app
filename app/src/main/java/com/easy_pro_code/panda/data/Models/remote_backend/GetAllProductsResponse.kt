package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GetAllProductsResponse(

	@field:SerializedName("products")
	val products: List<ProductsItem>? = null
) : Parcelable

@Parcelize
data class VariantItem(

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("Value")
	val value: List<String?>? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("Key")
	val key: String? = null
) : Parcelable

@Parcelize
data class ProductsItem(

	@field:SerializedName("Variant")
	val variant: List<VariantItem?>? = null,

	@field:SerializedName("multiImg")
	val multiImg: List<String?>? = null,

	@field:SerializedName("Category")
	val category: String? = null,

	@field:SerializedName("Price")
	val price: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("Image")
	val image: String? = null,

	@field:SerializedName("Prands")
	val prands: String? = null,

	@field:SerializedName("rate")
	val rate:Int?=null
) : Parcelable
