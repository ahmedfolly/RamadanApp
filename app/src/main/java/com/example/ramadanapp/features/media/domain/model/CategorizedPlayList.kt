package com.example.ramadanapp.features.media.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategorizedPlayList (
	val title:String,
	val videos: List<Video>
):Parcelable