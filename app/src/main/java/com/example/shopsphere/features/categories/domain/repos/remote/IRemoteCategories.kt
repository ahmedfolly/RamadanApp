package com.example.shopsphere.features.categories.domain.repos.remote

import com.example.shopsphere.features.categories.data.models.dtos.Categories

interface IRemoteCategories {
	suspend fun getRemoteCategories(): Categories
}