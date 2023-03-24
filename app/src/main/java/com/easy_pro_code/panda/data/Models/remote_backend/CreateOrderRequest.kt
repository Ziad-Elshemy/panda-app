package com.easy_pro_code.panda.data.Models.remote_backend

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateOrderRequest(

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("Cart")
	val cart: String? = null
) : Parcelable
