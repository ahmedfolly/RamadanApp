package com.example.shopsphere.features.categories.domain.repos

import com.example.shopsphere.features.categories.domain.models.Category

interface ICategoriesRepo {
	suspend fun getLocalCategories():List<Category>
	suspend fun getRemoteCategories(): List<Category>
}