package com.example.shopsphere.features.products.data.repos.remote

import android.util.Log
import com.example.shopsphere.common.domain.repos.remote.IRemoteProvider
import com.example.shopsphere.features.products.data.models.dtos.ProductsResponse
import com.example.shopsphere.features.products.domain.repos.remote.IProductsRemote

class ProductsRemote(private val remoteProvider: IRemoteProvider) : IProductsRemote {
	override suspend fun getAllProducts(): ProductsResponse {
		val queries = mapOf("limit" to "40")
		val response = remoteProvider.get<ProductsResponse>(
			endPoint = "products",
			returnType = ProductsResponse::class.java,
			queries = queries
		)
		return response
	}

	override suspend fun searchForProduct(productName: String): ProductsResponse {
		val queries = mapOf("q" to productName)
		return remoteProvider.get<ProductsResponse>(
			endPoint = "products/search",
			returnType = ProductsResponse::class.java,
			queries = queries
		)
	}

	override suspend fun getCategoryProducts(categoryName: String): ProductsResponse {
		val response = remoteProvider.get<ProductsResponse>(
			endPoint = "products/category/$categoryName",
			returnType = ProductsResponse::class.java
		)
		return response
	}
}