package com.example.shopsphere.features.categories.data.repos.remote

import android.util.Log
import com.example.shopsphere.common.domain.repos.remote.IRemoteProvider
import com.example.shopsphere.features.categories.data.models.dtos.Categories
import com.example.shopsphere.features.categories.domain.repos.remote.IRemoteCategories

class RemoteCategories(private val remoteProvider: IRemoteProvider): IRemoteCategories {
	override suspend fun getRemoteCategories(): Categories {
		val remoteCategories = remoteProvider.get<Categories>(endPoint = "products/categories", returnType = Categories::class.java)
		return remoteCategories
	}
}