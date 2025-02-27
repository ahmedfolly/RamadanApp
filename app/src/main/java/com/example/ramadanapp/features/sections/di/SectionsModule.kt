package com.example.ramadanapp.features.sections.di

import com.example.ramadanapp.common.domain.repos.remote.IRemoteProvider
import com.example.ramadanapp.features.media.domain.repos.IMediaRepo
import com.example.ramadanapp.features.sections.data.repos.MainSections
import com.example.ramadanapp.features.sections.data.repos.remote.RemoteMainSections
import com.example.ramadanapp.features.sections.domain.interactors.GetMainSectionsUC
import com.example.ramadanapp.features.sections.domain.repos.IMainSections
import com.example.ramadanapp.features.sections.domain.repos.remote.IRemoteMainSections
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