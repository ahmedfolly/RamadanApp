package com.da3wa.ramadanapp.features.media.di

import android.content.Context
import com.da3wa.ramadanapp.common.data.repos.local.AppDatabase
import com.da3wa.ramadanapp.common.domain.repos.remote.IRemoteProvider
import com.da3wa.ramadanapp.features.media.data.repos.MediaRepo
import com.da3wa.ramadanapp.features.media.data.repos.local.MediaDao
import com.da3wa.ramadanapp.features.media.data.repos.local.MediaLocal
import com.da3wa.ramadanapp.features.media.data.repos.remote.MediaRemote
import com.da3wa.ramadanapp.features.media.domain.interactors.DeleteVideoUC
import com.da3wa.ramadanapp.features.media.domain.interactors.GetLastSeenUC
import com.da3wa.ramadanapp.features.media.domain.interactors.GetMediaUC
import com.da3wa.ramadanapp.features.media.domain.interactors.GetSavedVideoByIdUC
import com.da3wa.ramadanapp.features.media.domain.interactors.GetSavedVideosUC
import com.da3wa.ramadanapp.features.media.domain.interactors.SaveLastSeenUC
import com.da3wa.ramadanapp.features.media.domain.interactors.SaveVideoUC
import com.da3wa.ramadanapp.features.media.domain.repos.IMediaRepo
import com.da3wa.ramadanapp.features.media.domain.repos.local.IMediaLocal
import com.da3wa.ramadanapp.features.media.domain.repos.remote.IMediaRemote
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object MediaModule {
	@Provides
	fun provideGetMediaUC(repo: IMediaRepo): GetMediaUC {
		return GetMediaUC(repo)
	}

	@Provides
	fun provideGetSavedVideoUC(repo: IMediaRepo): GetSavedVideoByIdUC {
		return GetSavedVideoByIdUC(repo)
	}

	@Provides
	fun provideGetSavedVideosUC(repo: IMediaRepo) = GetSavedVideosUC(repo)

	@Provides
	fun provideSaveVideoUC(repo: IMediaRepo) = SaveVideoUC(repo)

	@Provides
	fun provideDeleteUC(repo: IMediaRepo) = DeleteVideoUC(repo)

	@Provides
	fun provideSaveLastSeenVideoUC(repo: IMediaRepo) = SaveLastSeenUC(repo)

	@Provides
	fun provideGetLastSeenVideoUC(repo: IMediaRepo) = GetLastSeenUC(repo)

	@Provides
	fun provideIMediaRepo(mediaRemote: IMediaRemote, mediaLocal: IMediaLocal): IMediaRepo {
		return MediaRepo(mediaRemote, mediaLocal)
	}

	@Provides
	fun provideIMediaRemote(remoteProvider: IRemoteProvider): IMediaRemote {
		return MediaRemote(remoteProvider)
	}

	@Provides
	fun provideIMediaLocal(
		mediaDao: MediaDao,
		@ApplicationContext context: Context,
		gson: Gson
	): IMediaLocal {
		return MediaLocal(mediaDao, context, gson)
	}

	@Provides
	fun provideMediaDao(@ApplicationContext context: Context, database: AppDatabase): MediaDao {
		return database.getMediaDao()
	}

}