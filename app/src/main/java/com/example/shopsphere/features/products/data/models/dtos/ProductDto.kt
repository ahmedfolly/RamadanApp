package com.example.shopsphere.features.products.data.models.dtos

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class ProductDto(
	@SerializedName("availabilityStatus")
	val availabilityStatus: String? = null,
	@SerializedName("brand")
	val brand: String? = null,
	@SerializedName("category")
	val category: String? = null,
	@SerializedName("description")
	val description: String? = null,
	@SerializedName("dimensions")
	val dimensions: DimensionsDto? = null,
	@SerializedName("discountPercentage")
	val discountPercentage: Double? = null,
	@SerializedName("id")
	val id: Int? = null,
	@SerializedName("images")
	val images: List<String>? = null,
	@SerializedName("meta")
	val meta: MetaDto? = null,
	@SerializedName("minimumOrderQuantity")
	val minimumOrderQuantity: Int? = null,
	@SerializedName("price")
	val price: Double? = null,
	@SerializedName("rating")
	val rating: Double? = null,
	@SerializedName("returnPolicy")
	val returnPolicy: String? = null,
	@SerializedName("reviews")
	val reviews: List<ReviewDto>? = null,
	@SerializedName("shippingInformation")
	val shippingInformation: String? = null,
	@SerializedName("sku")
	val sku: String? = null,
	@SerializedName("stock")
	val stock: Int? = null,
	@SerializedName("tags")
	val tags: List<String>? = null,
	@SerializedName("thumbnail")
	val thumbnail: String? = null,
	@SerializedName("title")
	val title: String? = null,
	@SerializedName("warrantyInformation")
	val warrantyInformation: String? = null,
	@SerializedName("weight")
	val weight: Int? = null
)