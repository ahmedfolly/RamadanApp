package com.example.ramadanapp.features.media.presentation.playlist.mvi

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.ramadanapp.common.domain.models.Resource
import com.example.ramadanapp.common.presentation.RamadanAppViewModel
import com.example.ramadanapp.features.media.domain.interactors.DeleteVideoUC
import com.example.ramadanapp.features.media.domain.interactors.GetLastSeenUC
import com.example.ramadanapp.features.media.domain.interactors.GetSavedVideoByIdUC
import com.example.ramadanapp.features.media.domain.interactors.SaveLastSeenUC
import com.example.ramadanapp.features.media.domain.interactors.SaveVideoUC
import com.example.ramadanapp.features.media.domain.model.StagedData
import com.example.ramadanapp.features.media.domain.model.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
	private val getSavedVideoUC: GetSavedVideoByIdUC,
	private val saveVideoUC: SaveVideoUC,
	private val deleteVideoUC: DeleteVideoUC,
	private val saveLastSeenUC: SaveLastSeenUC,
) :
	RamadanAppViewModel<PlaylistIntent, PlaylistState>(
		PlaylistState.Idle
	) {
	private val _playlistState: MutableStateFlow<StagedData?> = MutableStateFlow(null)
	val playlistState = _playlistState.asStateFlow()

	private val _savedVideoState: MutableStateFlow<Boolean> = MutableStateFlow(false)
	val savedVideoState = _savedVideoState.asStateFlow()
	override fun processIntent() {
		viewModelScope.launch(Dispatchers.IO) {
			userIntentChannel.consumeAsFlow().collect { intent ->
				when (intent) {
					is PlaylistIntent.GetSavedVideoById -> getSavedVideoById(intent.videoId)
					is PlaylistIntent.SaveOrDeleteVideo -> saveOrDeleteVideo(intent.video)
					is PlaylistIntent.SaveLastSeenVideo -> saveLastSeenVideo(intent.video)
				}
			}
		}
	}

	private fun getSavedVideoById(videoId: String) {
		viewModelScope.launch(Dispatchers.IO) {
			val videoResponse = getSavedVideoUC(videoId)
			videoResponse.collect { response ->
				if (response is Resource.Success) {
					_savedVideoState.update { response.model.videoId.isNotEmpty() }
				}
			}
		}
	}

	fun setInitialPlaylistState(newPlaylist: StagedData) {
		if (_playlistState.value == null) {
			_playlistState.update { newPlaylist }
		}
	}

	fun updatePlaylistState(newPlaylist: StagedData) {
		_playlistState.update {
			newPlaylist
		}
	}

	private fun saveOrDeleteVideo(video: Video) {
		viewModelScope.launch {
			if (savedVideoState.value) {
				deleteVideoUC(video).collect { response ->
					if (response is Resource.Success) {
						_savedVideoState.update { false }
					}
				}
			} else {
				saveVideoUC(video).collect { response ->
					if (response is Resource.Success) {
						_savedVideoState.update { true }
					}
				}
			}
		}
	}

	private fun saveLastSeenVideo(video: Video) {
		viewModelScope.launch {
			 saveLastSeenUC(video).collect {response->
				if (response is Resource.Success){
					Log.d("TAG", "saveLastSeenVideo: ${response.model}")
				}
			}
		}
	}

	override fun clearState() {
		setState(PlaylistState.Idle)
	}
}