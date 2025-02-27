package com.da3wa.ramadanapp.features.sections.data.mappers

import com.da3wa.ramadanapp.common.domain.mapper.IMapper
import com.da3wa.ramadanapp.features.sections.data.models.dtos.SectionCategoryDto
import com.da3wa.ramadanapp.features.sections.data.models.dtos.SectionDto
import com.da3wa.ramadanapp.features.sections.domain.models.Section
import com.da3wa.ramadanapp.features.sections.domain.models.SectionCategory

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