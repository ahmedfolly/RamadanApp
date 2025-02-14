package com.example.shopsphere.features.products.data.models.entities

import android.R
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.shopsphere.features.products.data.mappers.DataConverter


@Entity(tableName = "products")
@TypeConverters(value = [DataConverter::class])
data class ProductEntity(
	val availabilityStatus: String,
	val brand: String,
	val category: String,
	val description: String,
	val discountPercentage: Double,
	@PrimaryKey
	val id: Int,
	val images: List<String>,
	val price: Double,
	val rating: Double,
	val reviews: List<ReviewEntity>,
	val tags: List<String>,
	val thumbnail: String,
	val title: String,
)