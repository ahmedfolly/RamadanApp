package com.da3wa.ramadanapp.common.domain.interactors

import com.da3wa.ramadanapp.common.domain.models.RamadanAppException
import com.da3wa.ramadanapp.common.domain.models.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUC<in Param, out Model> {
	abstract suspend fun execute(param: Param? = null): Model
	operator fun invoke(param: Param? = null) = flow {
		emit(Resource.Loading)
		val model = execute(param)
		emit(Resource.Success(model))
	}.catch { e ->
		emit(Resource.Failure(handleException(e)))
	}.flowOn(Dispatchers.IO)

	private fun handleException(e: Throwable): RamadanAppException {
		return if (e is RamadanAppException) e else RamadanAppException.UnknownException("Unknown error appeared")
	}
}