package com.example.ramadanapp.features.media.domain.repos.remote

import com.example.ramadanapp.features.media.data.model.dtos.PlaylistDto
import com.example.ramadanapp.features.media.data.model.dtos.VideoDto

interface IMediaRemote {
	suspend fun getMedia(): PlaylistDto
}