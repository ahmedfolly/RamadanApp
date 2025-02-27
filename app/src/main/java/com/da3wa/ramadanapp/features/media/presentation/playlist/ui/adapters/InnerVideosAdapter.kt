package com.da3wa.ramadanapp.features.media.presentation.playlist.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.da3wa.ramadanapp.common.utils.videoThumbnailUrl
import com.edu.da3wa.ramadanapp.databinding.VideoItemInnerBinding
import com.da3wa.ramadanapp.features.media.domain.model.Video

class InnerVideosAdapter(val onVideoClicker: OnVideoClicker): ListAdapter<Video, InnerVideosAdapter.PlayListVideosVH>(PlayListVideosDiffUtil()) {
	private lateinit var binding: VideoItemInnerBinding
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): PlayListVideosVH {
		binding = VideoItemInnerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
		return PlayListVideosVH(binding)
	}

	override fun onBindViewHolder(
		holder: PlayListVideosVH,
		position: Int
	) {
		val video = getItem(position)
		holder.binds(video)
	}

	inner class PlayListVideosVH(val binding: VideoItemInnerBinding): RecyclerView.ViewHolder(binding.root){
		fun binds(video:Video){
			with(binding){
				tvVideoTitleInner.text = video.title
				ivVideoImageInner.load(videoThumbnailUrl(video.videoId)){
					crossfade(true)
				}
				root.setOnClickListener{
					onVideoClicker.videoClicker(video)
				}
			}
		}
	}
	interface OnVideoClicker {
		fun videoClicker(video: Video)
	}
	class PlayListVideosDiffUtil: DiffUtil.ItemCallback<Video>(){
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