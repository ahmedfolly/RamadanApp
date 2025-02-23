package com.example.ramadanapp.features.media.presentation.main.mvi

import androidx.lifecycle.viewModelScope
import com.example.ramadanapp.common.domain.models.Resource
import com.example.ramadanapp.common.presentation.RamadanAppViewModel
import com.example.ramadanapp.features.media.domain.interactors.GetLastSeenUC
import com.example.ramadanapp.features.media.domain.interactors.GetMediaUC
import com.example.ramadanapp.features.media.domain.model.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MediaViewModel @Inject constructor(
	private val getMediaUC: GetMediaUC,
	private val getLastSeenUC: GetLastSeenUC
) : RamadanAppViewModel<MediaIntent, MediaState>(
	MediaState.Idle
) {
	private val _lastSeenVideoState: MutableStateFlow<Video?> = MutableStateFlow(null)
	val lastSeenVideoState = _lastSeenVideoState.asStateFlow()
	override fun processIntent() {
		viewModelScope.launch {
			userIntentChannel.consumeAsFlow().collect { userIntent ->
				when (userIntent) {
					MediaIntent.LoadMedia        -> loadMedia()
					MediaIntent.GetLastSeenVideo -> getLastSeenVideo()
				}
			}
		}
	}

	private fun loadMedia() {
		viewModelScope.launch {
			val mediaResponse = getMediaUC()
			mediaResponse.collect { response ->
				when (response) {
					is Resource.Failure -> setState(MediaState.Failure(response.exception))
					is Resource.Loading -> setState(MediaState.Loading)
					is Resource.Success -> setState(MediaState.Success(response.model))
				}
			}
		}
	}

	private fun getLastSeenVideo() {
		viewModelScope.launch {
			getLastSeenUC().collect { response ->
				if (response is Resource.Success) {
					_lastSeenVideoState.update {
						response.model
					}
				}
			}
		}
	}

	override fun clearState() {
		setState(MediaState.Idle)
	}
}