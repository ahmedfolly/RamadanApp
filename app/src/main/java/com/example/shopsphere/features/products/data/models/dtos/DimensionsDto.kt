package com.example.shopsphere.features.products.data.models.dtos

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class DimensionsDto(
    @SerializedName("depth")
    val depth: Double?=null,
    @SerializedName("height")
    val height: Double?=null,
    @SerializedName("width")
    val width: Double?=null
)