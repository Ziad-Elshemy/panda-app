package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GetOrdersRequest(

	@field:SerializedName("userId")
	val userId: String? = null
) : Parcelable