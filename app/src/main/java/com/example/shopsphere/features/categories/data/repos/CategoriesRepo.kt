package com.example.shopsphere.features.categories.data.repos

import android.util.Log
import com.example.shopsphere.features.categories.domain.models.Category
import com.example.shopsphere.features.categories.domain.repos.ICategoriesRepo
import com.example.shopsphere.features.categories.domain.repos.remote.IRemoteCategories
import com.example.shopsphere.features.categories.data.mapper.CategoryMapper.convertDtoToDomains

class CategoriesRepo(private val remoteCategories: IRemoteCategories): ICategoriesRepo {
	override suspend fun getLocalCategories(): List<Category> {
		return emptyList()
	}

	override suspend fun getRemoteCategories(): List<Category> {
		val remoteCategories = remoteCategories.getRemoteCategories().convertDtoToDomains()
		remoteCategories.forEach{
			Log.d("TAG", "getRemoteCategories: ${it.name}")
		}
		return remoteCategories
	}
}