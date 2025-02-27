package com.da3wa.ramadanapp.features.media.presentation.playlist.mvi

import com.da3wa.ramadanapp.common.domain.models.RamadanAppException
import com.da3wa.ramadanapp.features.media.domain.model.Playlist

sealed class PlaylistState {
	data object Idle: PlaylistState()
	data object Loading : PlaylistState()
	data class Success(val playlist: Playlist) : PlaylistState()
	data class Failure(val error: RamadanAppException) : PlaylistState()
}