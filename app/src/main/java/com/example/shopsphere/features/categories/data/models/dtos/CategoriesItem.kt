package com.example.shopsphere.features.categories.data.models.dtos

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class CategoriesItem(
	@SerializedName("name")
	val name: String? = null,
	@SerializedName("slug")
	val slug: String? = null,
	@SerializedName("url")
	val url: String? = null
)