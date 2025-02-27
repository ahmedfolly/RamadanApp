package com.da3wa.ramadanapp.features.sections.data.models.dtos

import com.google.gson.annotations.SerializedName

data class SectionDto(
	@SerializedName("title")
	val title: String? = null,
	@SerializedName("categories")
	val categories: List<SectionCategoryDto>? = null
)
