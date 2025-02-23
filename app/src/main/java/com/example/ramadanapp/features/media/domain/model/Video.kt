package com.example.ramadanapp.features.media.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    val category: String,
    val subCategory: String,
    val title: String,
    val url: String,
    val videoId:String
):Parcelable