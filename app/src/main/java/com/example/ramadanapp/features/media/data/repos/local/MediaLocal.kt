package com.example.ramadanapp.features.media.data.repos.local

import android.content.Context
import android.util.Log
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ramadanapp.features.media.data.model.entities.VideoEntity
import com.example.ramadanapp.features.media.domain.repos.local.IMediaLocal
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

private val Context.dataStore by preferencesDataStore("last_seen_video")

class MediaLocal(
	private val mediaDao: MediaDao,
	private val context: Context,
	private val gson: Gson
) : IMediaLocal {
	companion object {
		private val LAST_SEEN_VIDEO = stringPreferencesKey("last_seen_video")
	}

	override suspend fun saveVideo(videoEntity: VideoEntity) {
		mediaDao.insert(videoEntity)
	}

	override suspend fun getVideoById(videoId: String): VideoEntity {
		return mediaDao.getVideoById(videoId)
	}

	override suspend fun getVideos(): List<VideoEntity> {
		return mediaDao.getSavedVideos()
	}

	override suspend fun deleteVideo(video: VideoEntity) {
		mediaDao.deleteVideoById(video.videoId)
	}

	override suspend fun saveLastSeenVideo(video: VideoEntity) {
		//convert video to json string
		val videoJson = gson.toJson(video)
		Log.d("TAG", "saveLastSeenVideo: $videoJson")
		context.dataStore.edit { preferences ->
			preferences[LAST_SEEN_VIDEO] = videoJson
		}
	}

	override suspend fun getLastSeenVideo(): VideoEntity? {
		val videoJson = context.dataStore.data.first()[LAST_SEEN_VIDEO] ?: return null
		return gson.fromJson<VideoEntity>(videoJson, VideoEntity::class.java)
	}
}