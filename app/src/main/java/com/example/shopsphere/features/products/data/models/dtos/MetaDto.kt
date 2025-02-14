package com.example.shopsphere.features.products.data.models.dtos

import com.google.gson.annotations.SerializedName

data class MetaDto(
    @SerializedName("barcode")
    val barcode: String?=null,
    @SerializedName("createdAt")
    val createdAt: String?=null,
    @SerializedName("qrCode")
    val qrCode: String?=null,
    @SerializedName("updatedAt")
    val updatedAt: String?=null
)