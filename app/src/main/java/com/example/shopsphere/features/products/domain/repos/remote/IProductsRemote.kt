package com.example.shopsphere.features.products.domain.repos.remote

import com.example.shopsphere.features.products.data.models.dtos.ProductsResponse

interface IProductsRemote {
	suspend fun getAllProducts(): ProductsResponse
	suspend fun searchForProduct(productName:String): ProductsResponse
	suspend fun getCategoryProducts(categoryName: String): ProductsResponse
}