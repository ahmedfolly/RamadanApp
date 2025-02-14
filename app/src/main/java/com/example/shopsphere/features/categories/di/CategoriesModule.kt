package com.example.shopsphere.features.categories.di

import com.example.shopsphere.common.domain.repos.remote.IRemoteProvider
import com.example.shopsphere.features.categories.data.repos.CategoriesRepo
import com.example.shopsphere.features.categories.data.repos.remote.RemoteCategories
import com.example.shopsphere.features.categories.domain.interactors.GetCategoriesUC
import com.example.shopsphere.features.categories.domain.repos.ICategoriesRepo
import com.example.shopsphere.features.categories.domain.repos.remote.IRemoteCategories
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CategoriesModule {
	@Provides
	fun provideGetCategoriesUC(repo: ICategoriesRepo): GetCategoriesUC{
		return GetCategoriesUC(repo)
	}
	@Provides
	fun provideICategoriesRepo(iRemoteCategories: IRemoteCategories): ICategoriesRepo{
		return CategoriesRepo(iRemoteCategories)
	}
	@Provides
	fun provideIRemoteCategories(remoteProvider: IRemoteProvider): IRemoteCategories{
		return RemoteCategories(remoteProvider)
	}
}