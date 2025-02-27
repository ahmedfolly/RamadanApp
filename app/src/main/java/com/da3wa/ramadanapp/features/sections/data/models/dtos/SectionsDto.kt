package com.da3wa.ramadanapp.features.sections.data.models.dtos

import com.google.gson.annotations.SerializedName

data class SectionsDto(
	@SerializedName("sections")
	val sections: List<SectionDto>
)
