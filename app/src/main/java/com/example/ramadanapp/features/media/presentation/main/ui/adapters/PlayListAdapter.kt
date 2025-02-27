package com.example.ramadanapp.features.media.presentation.main.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ramadanapp.databinding.PlaylistItemBinding
import com.example.ramadanapp.features.media.domain.model.CategorizedPlayList
import com.example.ramadanapp.features.sections.presentation.ui.MainFragment

class PlayListAdapter(val mainFragment: MainFragment) :
	ListAdapter<CategorizedPlayList, PlayListAdapter.PlayListVH>(PlayListDiffUtil()) {
	private lateinit var _binding: PlaylistItemBinding
	private val binding
		get() = _binding

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): PlayListVH {
		_binding = PlaylistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return PlayListVH(binding)
	}

	override fun onBindViewHolder(
		holder: PlayListVH,
		position: Int
	) {
		val playlist = getItem(position)
		holder.binds(playlist)
	}

	inner class PlayListVH(val binding: PlaylistItemBinding) :
		RecyclerView.ViewHolder(binding.root) {
		private lateinit var videoAdapter: VideosAdapter
		fun binds(playList: CategorizedPlayList) {
			val playListVideos = playList.videos
			val playListCategory = playList.title
			videoAdapter = VideosAdapter(mainFragment,playListVideos)
			with(binding) {
				tvPlaylistName.text = playListCategory
				rvPlaylistVideos.apply {
					setHasFixedSize(true)
					layoutManager =
						LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
					adapter = videoAdapter
				}
				tvOpenPlaylist.setOnClickListener {
//					val action = MainFragmentDirections.Companion.actionMainFragmentToPlaylistFragment(playList)
//					mainFragment.findNavController().navigate(action)
				}
			}
		}
	}

	class PlayListDiffUtil : DiffUtil.ItemCallback<CategorizedPlayList>() {
		override fun areItemsTheSame(
			oldItem: CategorizedPlayList,
			newItem: CategorizedPlayList
		): Boolean {
			return oldItem.title == newItem.title
		}

		override fun areContentsTheSame(
			oldItem: CategorizedPlayList,
			newItem: CategorizedPlayList
		): Boolean {
			return oldItem == newItem
		}

	}
}