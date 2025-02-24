package com.example.ramadanapp.features.sections.data.models.dtos

import com.google.gson.annotations.SerializedName

data class SectionCategoryDto(
	@SerializedName("title")
	val title:String?=null,
	@SerializedName("url")
	val imageUrl:String?=null
)
