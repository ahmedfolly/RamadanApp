package com.example.shopsphere.features.products.data.repos.local

import androidx.room.Dao
import androidx.room.Query
import com.example.shopsphere.common.data.repos.local.BaseDao
import com.example.shopsphere.features.products.data.models.entities.ProductEntity

@Dao
interface ProductsDao: BaseDao<ProductEntity> {
	@Query("SELECT * FROM products")
	suspend fun getAllProducts(): List<ProductEntity>
}