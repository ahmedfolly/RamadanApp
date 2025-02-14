package com.example.shopsphere.features.products.domain.repos

import com.example.shopsphere.features.products.domain.models.Product

interface IProductsRepo {
	suspend fun getRemoteProducts(): List<Product>
	suspend fun getLocalProducts(): List<Product>
	suspend fun saveProducts(products: List<Product>)
	suspend fun searchForProducts(productName:String): List<Product>
	suspend fun getCategoryProducts(categoryName:String): List<Product>
}