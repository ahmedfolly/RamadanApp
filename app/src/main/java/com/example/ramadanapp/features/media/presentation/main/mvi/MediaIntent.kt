package com.example.ramadanapp.features.media.presentation.main.mvi

sealed class MediaIntent {
	data object LoadMedia: MediaIntent()
}