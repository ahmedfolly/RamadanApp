package com.example.ramadanapp.features.media.presentation.mvi

import androidx.lifecycle.viewModelScope
import com.example.ramadanapp.common.domain.models.Resource
import com.example.ramadanapp.common.presentation.RamadanAppViewModel
import com.example.ramadanapp.features.media.domain.interactors.GetMediaUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MediaViewModel @Inject constructor(private val getMediaUC: GetMediaUC): RamadanAppViewModel<MediaIntent, MediaState>(
	MediaState.Idle
) {
	override fun processIntent() {
		viewModelScope.launch{
			userIntentChannel.consumeAsFlow().collect{userIntent->
				when(userIntent){
					MediaIntent.LoadMedia -> loadMedia()
				}
			}
		}
	}

	private fun loadMedia(){
		viewModelScope.launch{
			val mediaResponse = getMediaUC()
			mediaResponse.collect{response->
				when(response){
					is Resource.Failure -> setState(MediaState.Failure(response.exception))
					is Resource.Loading    -> setState(MediaState.Loading)
					is Resource.Success -> setState(MediaState.Success(response.model))
				}
			}
		}
	}
	override fun clearState() {
		setState(MediaState.Idle)
	}
}