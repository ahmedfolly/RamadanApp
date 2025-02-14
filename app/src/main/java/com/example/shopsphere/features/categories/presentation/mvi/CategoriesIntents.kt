package com.example.shopsphere.features.categories.presentation.mvi

sealed class CategoriesIntents {
	data object GetCategories: CategoriesIntents()
}