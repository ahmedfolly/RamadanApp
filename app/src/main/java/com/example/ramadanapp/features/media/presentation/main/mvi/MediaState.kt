package com.example.ramadanapp.features.media.presentation.main.mvi

import com.example.ramadanapp.common.domain.models.RamadanAppException
import com.example.ramadanapp.features.media.domain.model.Playlist

sealed class MediaState {
	data object Idle: MediaState()
	data object Loading: MediaState()
	data class Success(val media: Playlist): MediaState()
	data class Failure(val e: RamadanAppException): MediaState()
}