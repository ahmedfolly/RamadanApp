package com.da3wa.ramadanapp.features.sections.domain.repos

import com.da3wa.ramadanapp.features.sections.domain.models.Section

interface IMainSections {
	suspend fun getMainSections(): List<Section>
}