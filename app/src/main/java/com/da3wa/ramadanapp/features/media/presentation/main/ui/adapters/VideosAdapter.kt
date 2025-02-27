package com.da3wa.ramadanapp.features.media.presentation.main.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.da3wa.ramadanapp.common.utils.videoThumbnailUrl
import com.edu.da3wa.ramadanapp.databinding.VideoItemMainBinding
import com.da3wa.ramadanapp.features.media.domain.model.StagedData
import com.da3wa.ramadanapp.features.media.domain.model.Video
import com.da3wa.ramadanapp.features.sections.presentation.ui.MainFragment

class VideosAdapter(val mainFragment: MainFragment, val videos: List<Video>): RecyclerView.Adapter<VideosAdapter.VideosVH>() {
	private lateinit var binding: VideoItemMainBinding
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): VideosVH {
		binding = VideoItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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
				root.setOnClickListener{
					val newObject = StagedData(video,videos)
//					val action = MainFragmentDirections.actionMainFragmentToPlaylistFragment(newObject)
//					mainFragment.findNavController().navigate(action)
				}
			}
		}
	}
}