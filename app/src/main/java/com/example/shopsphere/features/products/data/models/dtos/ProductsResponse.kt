package com.example.shopsphere.features.products.data.models.dtos

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("limit")
    val limit: Int?=null,
    @SerializedName("products")
    val products: List<ProductDto>?=null,
    @SerializedName("skip")
    val skip: Int?=null,
    @SerializedName("total")
    val total: Int?=null
)