package com.da3wa.ramadanapp.features.sections.di

import com.da3wa.ramadanapp.common.domain.repos.remote.IRemoteProvider
import com.da3wa.ramadanapp.features.sections.data.repos.MainSections
import com.da3wa.ramadanapp.features.sections.data.repos.remote.RemoteMainSections
import com.da3wa.ramadanapp.features.sections.domain.interactors.GetMainSectionsUC
import com.da3wa.ramadanapp.features.sections.domain.repos.IMainSections
import com.da3wa.ramadanapp.features.sections.domain.repos.remote.IRemoteMainSections
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object SectionsModule {
	@Provides
	fun provideGetSectionsUC(repo: IMainSections) = GetMainSectionsUC(repo)
	@Provides
	fun provideIMainSections(remoteSections: IRemoteMainSections): IMainSections{
		return MainSections(remoteSections)
	}
	@Provides
	fun provideIRemoteMainSections(remoteProvider: IRemoteProvider): IRemoteMainSections{
		return RemoteMainSections(remoteProvider)
	}
}