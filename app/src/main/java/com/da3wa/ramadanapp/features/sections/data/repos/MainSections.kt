package com.da3wa.ramadanapp.features.sections.data.repos

import android.util.Log
import com.da3wa.ramadanapp.features.sections.data.mappers.SectionMapper
import com.da3wa.ramadanapp.features.sections.domain.models.Section
import com.da3wa.ramadanapp.features.sections.domain.repos.IMainSections
import com.da3wa.ramadanapp.features.sections.domain.repos.remote.IRemoteMainSections

class MainSections(private val remoteMainSections: IRemoteMainSections): IMainSections {
	override suspend fun getMainSections(): List<Section> {
		val mainSections = remoteMainSections.getMainSections().sections
		mainSections.forEach {
			Log.d("TAG", "getMainSections main repo: ${it.title}")
		}
		return SectionMapper.convertDtosToDomains(mainSections)
		//map from sections dto to sections
	}
}