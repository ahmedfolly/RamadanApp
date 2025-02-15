package com.example.ramadanapp.features.media.domain.repos

import com.example.ramadanapp.features.media.domain.model.Playlist

interface IMediaRepo {
	suspend fun getMediaData(): Playlist
}