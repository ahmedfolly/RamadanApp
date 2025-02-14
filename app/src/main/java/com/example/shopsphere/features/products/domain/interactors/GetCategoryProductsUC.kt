package com.example.shopsphere.features.products.domain.interactors

import com.example.shopsphere.common.domain.models.Resource
import com.example.shopsphere.common.domain.models.ShopSphereException
import com.example.shopsphere.features.products.domain.repos.IProductsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCategoryProductsUC(private val productsRepo: IProductsRepo) {
	operator fun invoke(categoryName: String) = flow {
		emit(Resource.Loading)
		val categoryProducts = productsRepo.getCategoryProducts(categoryName)
		emit(Resource.Success(categoryProducts))
	}.catch { e ->
		val exception = if (e is ShopSphereException) e
		else ShopSphereException.UnknownException("Unknown error occurred ${e.message}")
		emit(Resource.Failure(exception))
	}.flowOn(Dispatchers.IO)
}