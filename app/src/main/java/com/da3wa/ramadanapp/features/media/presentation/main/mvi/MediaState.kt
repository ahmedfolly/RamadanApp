package com.da3wa.ramadanapp.features.media.presentation.main.mvi

import com.da3wa.ramadanapp.common.domain.models.RamadanAppException
import com.da3wa.ramadanapp.features.media.domain.model.Playlist

sealed class MediaState {
	data object Idle: MediaState()
	data object Loading: MediaState()
	data class Success(val media: Playlist): MediaState()
	data class Failure(val e: RamadanAppException): MediaState()
}