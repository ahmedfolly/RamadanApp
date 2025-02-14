package com.example.shopsphere.features.categories.presentation.mvi

import com.example.shopsphere.common.domain.models.ShopSphereException
import com.example.shopsphere.features.categories.domain.models.Category

sealed class CategoriesState {
	data object Idle : CategoriesState()
	data object Loading : CategoriesState()
	data class Success(val categories: List<Category>) : CategoriesState()
	data class Failure(val e: ShopSphereException) : CategoriesState()
}