package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GetOrdersRequest(

	@field:SerializedName("userId")
	val userId: String? = "641a4af1c1e547cf6c2516cf"
) : Parcelable
