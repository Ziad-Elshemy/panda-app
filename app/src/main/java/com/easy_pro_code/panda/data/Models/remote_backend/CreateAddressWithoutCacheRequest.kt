package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CreateAddresswithoutCacheRequest(

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("Location")
	val location: Location? = null,

	@field:SerializedName("Name")
	val name: String? = null
) : Parcelable

@Parcelize
data class Location(

	@field:SerializedName("long")
	val jsonMemberLong: String? = null,

	@field:SerializedName("lat")
	val lat: String? = null
) : Parcelable
