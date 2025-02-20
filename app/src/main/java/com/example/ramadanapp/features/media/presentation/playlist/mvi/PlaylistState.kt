package com.example.ramadanapp.features.media.presentation.playlist.mvi

import com.example.ramadanapp.common.domain.models.RamadanAppException
import com.example.ramadanapp.features.media.domain.model.Video

sealed class PlaylistState {
	data object Idle: PlaylistState()
	data object Loading : PlaylistState()
	data class Success(val video: Video) : PlaylistState()
	data class Failure(val error: RamadanAppException) : PlaylistState()
}