package com.da3wa.ramadanapp.features.sections.data.repos.remote

import android.util.Log
import com.da3wa.ramadanapp.common.domain.repos.remote.IRemoteProvider
import com.da3wa.ramadanapp.features.media.data.repos.remote.MediaRemote.Companion.MEDIA_END_POINT
import com.da3wa.ramadanapp.features.sections.data.models.dtos.SectionsDto
import com.da3wa.ramadanapp.features.sections.domain.repos.remote.IRemoteMainSections

class RemoteMainSections(private val remoteProvider: IRemoteProvider) : IRemoteMainSections {
	override suspend fun getMainSections(): SectionsDto {
		val sectionsResponse = remoteProvider.get<SectionsDto>(
			endPoint = MEDIA_END_POINT,
			returnType = SectionsDto::class.java
		)
		sectionsResponse.sections.forEach {
			Log.d("TAG", "getMainSections remote:${it.title} ")
		}
		return sectionsResponse
	}
}