package com.example.ramadanapp.features.media.presentation.saved.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ramadanapp.common.utils.videoThumbnailUrl
import com.example.ramadanapp.databinding.VideoItemInnerBinding
import com.example.ramadanapp.features.media.domain.model.Video

class SavedVideosAdapter: ListAdapter<Video, SavedVideosAdapter.SavedVideosVH>(SavedVideosDiffUtil()) {
	private lateinit var binding: VideoItemInnerBinding
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): SavedVideosVH {
		binding = VideoItemInnerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
		return SavedVideosVH(binding)
	}

	override fun onBindViewHolder(
		holder: SavedVideosVH,
		position: Int
	) {
		val video = getItem(position)
		holder.binds(video)
	}


	inner class SavedVideosVH(val binding: VideoItemInnerBinding): RecyclerView.ViewHolder(binding.root){
		fun binds(video: Video){
			with(binding){
				ivVideoImageInner.load(videoThumbnailUrl(video.videoId)){
					crossfade(true)
				}
				tvVideoTitleInner.text = video.title
			}
		}
	}
	class SavedVideosDiffUtil: DiffUtil.ItemCallback<Video>(){
		override fun areItemsTheSame(
			oldItem: Video,
			newItem: Video
		): Boolean {
			return oldItem.videoId == newItem.videoId
		}

		override fun areContentsTheSame(
			oldItem: Video,
			newItem: Video
		): Boolean {
			return oldItem == newItem
		}

	}
}