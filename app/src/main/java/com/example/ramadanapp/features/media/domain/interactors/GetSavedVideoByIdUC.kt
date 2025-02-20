package com.example.ramadanapp.features.media.domain.interactors

import android.util.Log
import com.example.ramadanapp.common.domain.models.RamadanAppException
import com.example.ramadanapp.common.domain.models.Resource
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.media.domain.repos.IMediaRepo

class GetSavedVideoByIdUC(private val mediaRepo: IMediaRepo) {
	suspend operator fun invoke(videoId:String): Resource<Video> {
		return try {
			Resource.Loading
			val video = mediaRepo.getSavedVideoById(videoId)
			Resource.Success(video)
		} catch (e: RamadanAppException) {
			Log.d("TAG", "invoke: ${e.message}")
			Resource.Failure(e)
		}catch (e: Exception){
			Log.d("TAG", "invoke: ${e.message}")
			Resource.Failure(RamadanAppException.UnknownException(e.message))
		}
	}
}