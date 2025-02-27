package com.da3wa.ramadanapp.features.media.domain.repos.remote

import com.da3wa.ramadanapp.features.media.data.model.dtos.PlaylistDto

interface IMediaRemote {
	suspend fun getMedia(): PlaylistDto
}