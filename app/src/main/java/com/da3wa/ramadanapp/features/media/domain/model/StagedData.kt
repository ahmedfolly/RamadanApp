package com.da3wa.ramadanapp.features.media.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class StagedData(
	val playingVideo: Video,
	val relatedVideos: List<Video>
): Parcelable
