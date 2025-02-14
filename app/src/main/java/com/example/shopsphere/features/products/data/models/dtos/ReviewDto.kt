package com.example.shopsphere.features.products.data.models.dtos

import com.google.gson.annotations.SerializedName

data class ReviewDto(
	@SerializedName("comment")
	val comment: String? = null,
	@SerializedName("date")
	val date: String? = null,
	@SerializedName("rating")
	val rating: Int? = null,
	@SerializedName("reviewerEmail")
	val reviewerEmail: String? = null,
	@SerializedName("reviewerName")
	val reviewerName: String? = null
)