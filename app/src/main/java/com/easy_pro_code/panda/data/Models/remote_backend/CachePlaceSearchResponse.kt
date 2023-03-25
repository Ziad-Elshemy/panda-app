package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CachePlaceSearchResponse(

	@field:SerializedName("orders")
	val orders: Orders? = null
) : Parcelable

@Parcelize
data class Orders(

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("Location")
	val location: Location? = null,

	@field:SerializedName("Name")
	val name: String? = null
) : Parcelable


