package com.example.shopsphere.features.products.di

import com.example.shopsphere.common.data.repos.local.AppDatabase
import com.example.shopsphere.common.domain.repos.remote.IRemoteProvider
import com.example.shopsphere.features.products.data.repos.ProductsRepo
import com.example.shopsphere.features.products.data.repos.local.ProductsDao
import com.example.shopsphere.features.products.data.repos.local.RoomLocalProducts
import com.example.shopsphere.features.products.data.repos.remote.ProductsRemote
import com.example.shopsphere.features.products.domain.interactors.GetAllProductsUC
import com.example.shopsphere.features.products.domain.interactors.GetCategoryProductsUC
import com.example.shopsphere.features.products.domain.interactors.SearchForProductUC
import com.example.shopsphere.features.products.domain.repos.IProductsRepo
import com.example.shopsphere.features.products.domain.repos.local.ILocalProducts
import com.example.shopsphere.features.products.domain.repos.remote.IProductsRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ProductsModule {
	@Provides
	fun provideGetAllProductsUC(repo: IProductsRepo): GetAllProductsUC{
		return GetAllProductsUC(repo)
	}
	@Provides
	fun provideSearchForProductsUC(repo: IProductsRepo): SearchForProductUC{
		return SearchForProductUC(repo)
	}
	@Provides
	fun provideGetCategoryProductsUC(repo: IProductsRepo): GetCategoryProductsUC{
		return GetCategoryProductsUC(repo)
	}
	@Provides
	fun provideProductsRepo(productsRemote: IProductsRemote,localProducts: ILocalProducts): IProductsRepo{
		return ProductsRepo(productsRemote,localProducts)
	}
	@Provides
	fun provideProductsRemote(remoteProvider: IRemoteProvider): IProductsRemote{
		return ProductsRemote(remoteProvider)
	}
	@Provides
	fun providesILocalProducts(dao: ProductsDao): ILocalProducts{
		return RoomLocalProducts(dao)
	}
	@Provides
	fun providesProductsDao(room: AppDatabase): ProductsDao{
		return room.getProductsDao()
	}
}