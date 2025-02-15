package com.example.ramadanapp.features.media.presentation.mvi

sealed class MediaIntent {
	data object LoadMedia: MediaIntent()
}