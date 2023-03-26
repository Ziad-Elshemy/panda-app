package com.easy_pro_code.panda.HomeFlow.models

import android.os.Parcelable
import com.easy_pro_code.panda.data.Models.remote_backend.OrderItemsItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderItems(val items: List<OrderItemsItem?>?): Parcelable
