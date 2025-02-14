package com.example.shopsphere.features.products.data.repos.local

import com.example.shopsphere.features.products.data.models.entities.ProductEntity
import com.example.shopsphere.features.products.domain.repos.local.ILocalProducts

class RoomLocalProducts(private val dao: ProductsDao) : ILocalProducts {
	override suspend fun saveProducts(products: List<ProductEntity>) {
		dao.insertAll(products)
	}

	override suspend fun getProducts(): List<ProductEntity> {
		return dao.getAllProducts()
	}
}
