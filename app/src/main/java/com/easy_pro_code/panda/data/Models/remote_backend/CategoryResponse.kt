package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CategoryResponse(

	@field:SerializedName("category")
	val category: List<CategoryItem>? = null
) : Parcelable

@Parcelize
data class CategoryItem(

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("Main")
	val main: String? = null
) : Parcelable
