package com.example.ramadanapp.features.media.presentation.playlist.mvi

import androidx.lifecycle.viewModelScope
import com.example.ramadanapp.common.domain.models.Resource
import com.example.ramadanapp.common.presentation.RamadanAppViewModel
import com.example.ramadanapp.features.media.domain.interactors.DeleteVideoUC
import com.example.ramadanapp.features.media.domain.interactors.GetSavedVideoByIdUC
import com.example.ramadanapp.features.media.domain.interactors.GetSavedVideosUC
import com.example.ramadanapp.features.media.domain.interactors.SaveVideoUC
import com.example.ramadanapp.features.media.domain.model.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
	private val getSavedVideoUC: GetSavedVideoByIdUC,
	private val saveVideoUC: SaveVideoUC,
	private val deleteVideoUC: DeleteVideoUC
) :
	RamadanAppViewModel<PlaylistIntent, PlaylistState>(
		PlaylistState.Idle
	) {
	override fun processIntent() {
		viewModelScope.launch(Dispatchers.IO) {
			userIntentChannel.consumeAsFlow().collect { intent ->
				when (intent) {
					is PlaylistIntent.GetSavedVideoById -> getSavedVideoById(intent.videoId)
					is PlaylistIntent.SaveVideo         -> saveVideo(intent.video)
					is PlaylistIntent.DeleteVideo       -> deleteVideo(intent.video)
				}
			}
		}
	}

	private fun getSavedVideoById(videoId: String) {
		viewModelScope.launch(Dispatchers.IO) {
			val videoResponse = getSavedVideoUC(videoId)
			handleResponse(videoResponse)
		}
	}

	private fun saveVideo(video: Video) {
		viewModelScope.launch(Dispatchers.IO) {
			val savingResponse = saveVideoUC(video)
			handleResponse(savingResponse)
		}
	}

	private fun handleResponse(videoResponse: Resource<Video>) {
		when (videoResponse) {
			is Resource.Failure -> setState(PlaylistState.Failure(videoResponse.exception))
			Resource.Loading    -> setState(PlaylistState.Loading)
			is Resource.Success -> setState(PlaylistState.Success(videoResponse.model))
		}
	}

	private fun deleteVideo(video: Video){
		viewModelScope.launch(Dispatchers.IO){
			val deleteResponse = deleteVideoUC(video)
			when(deleteResponse){
				is Resource.Failure -> setState(PlaylistState.Failure(deleteResponse.exception))
				Resource.Loading    -> setState(PlaylistState.Loading)
				is Resource.Success -> setState(PlaylistState.Success(deleteResponse.model))
			}
		}
	}
	override fun clearState() {
		setState(PlaylistState.Idle)
	}
}