package com.example.ramadanapp.common.utils

import com.example.ramadanapp.common.domain.models.Resource
import com.example.ramadanapp.common.domain.models.RamadanAppException

inline fun <reified T> handleResponse(
	response: Resource<T>,
	onFailure: (RamadanAppException) -> Unit,
	onSuccess: (T) -> Unit,
	onLoading:()->Unit
) {
	when (response) {
		is Resource.Failure -> onFailure(response.exception)
		is Resource.Loading -> onLoading()
		is Resource.Success -> onSuccess(response.model)
	}
}
fun videoThumbnailUrl(videoId:String):String{
	return  "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
}