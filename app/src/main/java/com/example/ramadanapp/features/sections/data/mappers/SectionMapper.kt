package com.example.ramadanapp.features.sections.data.mappers

import com.example.ramadanapp.common.domain.mapper.IMapper
import com.example.ramadanapp.features.sections.data.models.dtos.SectionCategoryDto
import com.example.ramadanapp.features.sections.data.models.dtos.SectionDto
import com.example.ramadanapp.features.sections.domain.models.Section
import com.example.ramadanapp.features.sections.domain.models.SectionCategory

object SectionMapper : IMapper<Section, SectionDto, Unit>() {
	override fun fromDtoToDomain(dto: SectionDto): Section {
		return Section(
			title = dto.title.orEmpty(),
			categories = dto.categories?.toDomain().orEmpty()
		)
	}

	private fun List<SectionCategoryDto>.toDomain(): List<SectionCategory> {
		return map { dto -> dto.toSectionCategory() }
	}

	private fun SectionCategoryDto.toSectionCategory(): SectionCategory {
		return SectionCategory(
			title = title.orEmpty(),
			imageUrl = imageUrl.orEmpty()
		)
	}
}