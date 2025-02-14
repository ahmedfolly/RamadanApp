package com.example.shopsphere.features.products.presentation.mvi

import com.example.shopsphere.features.products.domain.models.Product

sealed class UserIntents {
	data object LoadProducts: UserIntents()
	data class SearchForProduct(val productName:String): UserIntents()
	data class CategoryProducts(val categoryName:String): UserIntents()
}