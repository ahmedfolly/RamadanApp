package com.example.ramadanapp.features.sections.domain.repos

import com.example.ramadanapp.features.sections.domain.models.Section

interface IMainSections {
	suspend fun getMainSections(): List<Section>
}