package com.example.shopsphere.common.utils

import com.example.shopsphere.common.domain.models.Resource
import com.example.shopsphere.common.domain.models.ShopSphereException

inline fun <reified T> handleResponse(
	response: Resource<T>,
	onFailure: (ShopSphereException) -> Unit,
	onSuccess: (T) -> Unit,
	onLoading:()->Unit
) {
	when (response) {
		is Resource.Failure -> onFailure(response.exception)
		is Resource.Loading -> onLoading()
		is Resource.Success -> onSuccess(response.model)
	}
}