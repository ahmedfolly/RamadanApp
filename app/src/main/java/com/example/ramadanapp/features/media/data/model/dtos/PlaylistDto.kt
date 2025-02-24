package com.example.ramadanapp.features.media.data.model.dtos

import com.example.ramadanapp.features.media.domain.model.Video
import com.google.gson.annotations.SerializedName

data class PlaylistDto(
    @SerializedName("items")
    val videos: List<VideoDto>?=null
)