package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CreateAddresswithCacheResponse(

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable
