package com.da3wa.ramadanapp.features.sections.domain.repos.remote

import com.da3wa.ramadanapp.features.sections.data.models.dtos.SectionsDto

interface IRemoteMainSections {
	suspend fun getMainSections(): SectionsDto
}