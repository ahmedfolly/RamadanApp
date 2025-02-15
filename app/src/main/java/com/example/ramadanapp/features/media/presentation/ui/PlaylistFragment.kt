package com.example.ramadanapp.features.media.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramadanapp.databinding.FragmentPlaylistBinding
import com.example.ramadanapp.features.media.domain.model.StagedData
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.media.presentation.ui.adapters.InnerVideosAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class PlaylistFragment : Fragment(), InnerVideosAdapter.OnVideoClicker {
	private val playlistFragmentArgs: PlaylistFragmentArgs by navArgs()
	private lateinit var binding: FragmentPlaylistBinding
	private lateinit var innerVideosAdapter: InnerVideosAdapter
	private lateinit var youTubePlayerInstance: YouTubePlayer

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		innerVideosAdapter = InnerVideosAdapter(this)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentPlaylistBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupInnerVideosContainer()
		val playlist = playlistFragmentArgs.recentPlayList
		val videos = playlist.relatedVideos
		innerVideosAdapter.submitList(videos)
		setupViews(playlist)
		setupSelectedVideo(playlist.playingVideo.videoId)
	}

	private fun setupSelectedVideo(videoId: String) {
		lifecycle.addObserver(binding.youtubePlayerView)
		binding.youtubePlayerView.addYouTubePlayerListener(object :
			AbstractYouTubePlayerListener() {
			override fun onReady(youTubePlayer: YouTubePlayer) {
				super.onReady(youTubePlayer)
				youTubePlayerInstance = youTubePlayer
				Log.d("TAG", "onReady: $videoId")
				playSelectedVideo(videoId)
			}
		})
	}

	private fun playSelectedVideo(videoId: String) {
		youTubePlayerInstance.loadVideo(videoId, 0f)
	}

	private fun setupViews(playlist: StagedData) {
		with(binding) {
			tvPlayingVideoTitle.text = playlist.playingVideo.title
			tvPlayingVideoCategory.text = playlist.playingVideo.subCategory
		}
	}

	private fun setupInnerVideosContainer() {
		binding.rvVideosContainerInsidePlaylist.apply {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(requireContext())
			adapter = innerVideosAdapter
		}
	}

	private fun changePlayingVideoData(video: Video) {
		with(binding) {
			tvPlayingVideoTitle.text = video.title
			tvPlayingVideoCategory.text = video.category
		}

	}

	override fun videoClicker(video: Video) {
		playSelectedVideo(video.videoId)
		changePlayingVideoData(video)
	}
}