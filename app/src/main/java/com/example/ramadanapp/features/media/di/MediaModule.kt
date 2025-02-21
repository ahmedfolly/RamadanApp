package com.example.ramadanapp.features.media.di

import android.content.Context
import androidx.room.Room
import com.example.ramadanapp.common.data.repos.local.AppDatabase
import com.example.ramadanapp.common.domain.repos.remote.IRemoteProvider
import com.example.ramadanapp.features.media.data.repos.MediaRepo
import com.example.ramadanapp.features.media.data.repos.local.MediaDao
import com.example.ramadanapp.features.media.data.repos.local.MediaLocalDao
import com.example.ramadanapp.features.media.data.repos.remote.MediaRemote
import com.example.ramadanapp.features.media.domain.interactors.DeleteVideoUC
import com.example.ramadanapp.features.media.domain.interactors.GetMediaUC
import com.example.ramadanapp.features.media.domain.interactors.GetSavedVideoByIdUC
import com.example.ramadanapp.features.media.domain.interactors.GetSavedVideosUC
import com.example.ramadanapp.features.media.domain.interactors.SaveVideoUC
import com.example.ramadanapp.features.media.domain.repos.IMediaRepo
import com.example.ramadanapp.features.media.domain.repos.local.IMediaLocal
import com.example.ramadanapp.features.media.domain.repos.remote.IMediaRemote
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
	fun provideIMediaRepo(mediaRemote: IMediaRemote, mediaLocal: IMediaLocal): IMediaRepo {
		return MediaRepo(mediaRemote, mediaLocal)
	}

	@Provides
	fun provideIMediaRemote(remoteProvider: IRemoteProvider): IMediaRemote {
		return MediaRemote(remoteProvider)
	}

	@Provides
	fun provideIMediaLocal(mediaDao: MediaDao): IMediaLocal {
		return MediaLocalDao(mediaDao)
	}

	@Provides
	fun provideMediaDao(@ApplicationContext context: Context, database: AppDatabase): MediaDao {
		return database.getMediaDao()
	}
}