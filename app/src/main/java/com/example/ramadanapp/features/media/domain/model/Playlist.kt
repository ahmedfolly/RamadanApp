package com.example.ramadanapp.features.media.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class Playlist(
    val videos: List<Video>
)