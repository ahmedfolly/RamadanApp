package com.da3wa.ramadanapp.features.media.data.model.dtos

import com.google.gson.annotations.SerializedName

data class PlaylistDto(
    @SerializedName("items")
    val videos: List<VideoDto>?=null
)