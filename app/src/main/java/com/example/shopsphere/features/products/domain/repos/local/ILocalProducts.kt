package com.example.shopsphere.features.products.domain.repos.local

import com.example.shopsphere.features.products.data.models.entities.ProductEntity

interface ILocalProducts {
	suspend fun saveProducts(products: List<ProductEntity>)
	suspend fun getProducts(): List<ProductEntity>
}