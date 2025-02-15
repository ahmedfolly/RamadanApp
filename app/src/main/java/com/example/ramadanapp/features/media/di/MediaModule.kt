package com.example.ramadanapp.features.media.di

import com.example.ramadanapp.common.domain.repos.remote.IRemoteProvider
import com.example.ramadanapp.features.media.data.repos.MediaRepo
import com.example.ramadanapp.features.media.data.repos.remote.MediaRemote
import com.example.ramadanapp.features.media.domain.interactors.GetMediaUC
import com.example.ramadanapp.features.media.domain.repos.IMediaRepo
import com.example.ramadanapp.features.media.domain.repos.remote.IMediaRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object MediaModule {
	@Provides
	fun provideGetMediaUC(repo: IMediaRepo): GetMediaUC{
		return GetMediaUC(repo)
	}
	@Provides
	fun provideIMediaRepo(mediaRemote: IMediaRemote): IMediaRepo{
		return MediaRepo(mediaRemote)
	}
	@Provides
	fun provideIMediaRemote(remoteProvider: IRemoteProvider): IMediaRemote{
		return MediaRemote(remoteProvider)
	}
}