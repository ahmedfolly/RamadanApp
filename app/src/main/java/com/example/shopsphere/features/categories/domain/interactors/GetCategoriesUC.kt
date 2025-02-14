package com.example.shopsphere.features.categories.domain.interactors

import com.example.shopsphere.common.domain.models.Resource
import com.example.shopsphere.common.domain.models.ShopSphereException
import com.example.shopsphere.features.categories.domain.repos.ICategoriesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCategoriesUC(private val categoriesRepo: ICategoriesRepo) {
	operator fun invoke() = flow {
		emit(Resource.Loading)
		val categoriesRemote = categoriesRepo.getRemoteCategories()
		emit(Resource.Success(categoriesRemote))
	}.catch { e ->
		val categoriesLocal = categoriesRepo.getLocalCategories()
		if (categoriesLocal.isNotEmpty())
			emit(Resource.Success(categoriesLocal))
		else {
			val exception =
				if (e is ShopSphereException) e else ShopSphereException.UnknownException("Unknown error occurred while getting categories ${e.message}")
			emit(Resource.Failure(exception))
		}
	}.flowOn(Dispatchers.IO)
}