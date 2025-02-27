package com.da3wa.ramadanapp.features.media.presentation.saved.mvi

import com.da3wa.ramadanapp.common.domain.models.RamadanAppException
import com.da3wa.ramadanapp.features.media.domain.model.Video

sealed class SavedVideosScreenState {
	data object Idle: SavedVideosScreenState()
	data object Loading : SavedVideosScreenState()
	data class Success(val videos: List<Video>) : SavedVideosScreenState()
	data class Failure(val error: RamadanAppException) : SavedVideosScreenState()
}