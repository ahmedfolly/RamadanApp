package com.example.shopsphere.features.products.domain.models


data class Product(
	val availabilityStatus: String,
	val brand: String,
	val category: String,
	val description: String,
	val discountPercentage: Double,
	val id: Int,
	val images: List<String>,
	val price: Double,
	val rating: Double,
	val reviews: List<Review>,
	val tags: List<String>,
	val thumbnail: String,
	val title: String,
)