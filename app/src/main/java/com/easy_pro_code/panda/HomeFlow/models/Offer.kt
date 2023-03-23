package com.easy_pro_code.panda.HomeFlow.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Offer(val product: Product,val newPrice:String): Parcelable
