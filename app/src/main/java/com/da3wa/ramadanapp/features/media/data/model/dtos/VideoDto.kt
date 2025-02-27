package com.da3wa.ramadanapp.features.media.data.model.dtos

import com.google.gson.annotations.SerializedName

data class VideoDto(
	@SerializedName("category")
	val category: String? = null,
	@SerializedName("subCategory")
	val subCategory: String? = null,
	@SerializedName("title")
	val title: String? = null,
	@SerializedName("url")
	val url: String? = null
)