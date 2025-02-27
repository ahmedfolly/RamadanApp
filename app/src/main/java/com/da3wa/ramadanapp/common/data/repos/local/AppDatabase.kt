package com.da3wa.ramadanapp.common.data.repos.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.da3wa.ramadanapp.features.media.data.model.entities.VideoEntity
import com.da3wa.ramadanapp.features.media.data.repos.local.MediaDao

@Database(entities = [VideoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
	abstract fun getMediaDao(): MediaDao
}