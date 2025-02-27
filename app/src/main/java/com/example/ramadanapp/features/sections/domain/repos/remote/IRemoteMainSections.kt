package com.example.ramadanapp.features.sections.domain.repos.remote

import com.example.ramadanapp.features.sections.data.models.dtos.SectionsDto

interface IRemoteMainSections {
	suspend fun getMainSections(): SectionsDto
}