package com.example.ramadanapp.common.data.repos.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {
	@Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
	suspend fun insertAll(data:List<T>)
	@Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
	suspend fun insert(data:T)
	@Delete
	suspend fun delete(data:T)
	@Update
	suspend fun update(data:T)
}