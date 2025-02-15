package com.example.ramadanapp.common.data.repos.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ramadanapp.features.products.data.models.entities.ProductEntity
import com.example.ramadanapp.features.products.data.repos.local.ProductsDao

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
	abstract fun getProductsDao() : ProductsDao
}