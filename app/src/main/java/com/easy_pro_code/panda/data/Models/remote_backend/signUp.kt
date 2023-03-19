package com.easy_pro_code.panda.data.Models.remote_backend

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class SignUp(

	@field:SerializedName("message")
	val message: String? = null,
) : Parcelable

@Parcelize
data class SignUpRequest(

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("Phone")
	val phone: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("userName")
	val userName: String? = null
) : Parcelable
