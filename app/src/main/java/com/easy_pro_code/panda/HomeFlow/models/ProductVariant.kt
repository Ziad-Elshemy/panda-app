package com.easy_pro_code.panda.HomeFlow.models

import android.os.Parcelable
import com.easy_pro_code.panda.data.Models.remote_backend.VariantItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductVariant(
    val variant: List<VariantItem?>? = null
):Parcelable