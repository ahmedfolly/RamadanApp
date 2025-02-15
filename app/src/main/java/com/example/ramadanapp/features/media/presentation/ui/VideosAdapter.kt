package com.example.ramadanapp.features.media.presentation.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ramadanapp.databinding.PlaylistItemBinding
import com.example.ramadanapp.databinding.VideoItemMainBinding
import com.example.ramadanapp.features.media.domain.model.Video

class VideosAdapter(val videos: List<Video>): RecyclerView.Adapter<VideosAdapter.VideosVH>() {
	private lateinit var _binding: VideoItemMainBinding
	private val binding
		get() = _binding
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): VideosVH {
		_binding = VideoItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false)
		return VideosVH(binding)
	}

	override fun onBindViewHolder(
		holder: VideosVH,
		position: Int
	) {
		val video = videos[position]
		holder.binds(video)
	}

	override fun getItemCount() = videos.size

	inner class VideosVH(val binding: VideoItemMainBinding): RecyclerView.ViewHolder(binding.root){
		fun binds(video: Video){
			val videoTitle = video.title
			val videoCategory = video.subCategory
			val videoId = video.videoId
			with(binding){
				tvVideoTitle.text = videoTitle
				tvVideoCategory.text = videoCategory
				ivVideo.load(videoThumbnailUrl(videoId))
			}
		}
		private fun videoThumbnailUrl(videoId:String):String{
			return  "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
		}
	}
}