package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CachePlaceSearchRequest(

	@field:SerializedName("Location")
	val location: Location? = null
) : Parcelable

@Parcelize
data class Location(

	@field:SerializedName("long")
	val jsonMemberLong: String? = null,

	@field:SerializedName("lat")
	val lat: String? = null
) : Parcelable
