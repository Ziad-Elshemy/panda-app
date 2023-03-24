package com.easy_pro_code.panda.data.Models.remote_backend

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateOrderResponse(

	@field:SerializedName("success")
	val success: String? = null
) : Parcelable
