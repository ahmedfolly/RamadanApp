package com.example.ramadanapp.features.media.presentation.playlist.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramadanapp.databinding.FragmentPlaylistBinding
import com.example.ramadanapp.features.media.domain.model.StagedData
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.media.presentation.playlist.mvi.PlaylistIntent
import com.example.ramadanapp.features.media.presentation.playlist.mvi.PlaylistState
import com.example.ramadanapp.features.media.presentation.playlist.mvi.PlaylistViewModel
import com.example.ramadanapp.features.media.presentation.playlist.ui.adapters.InnerVideosAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.example.ramadanapp.R

@AndroidEntryPoint
class PlaylistFragment : Fragment(), InnerVideosAdapter.OnVideoClicker {
	private val playlistFragmentArgs: PlaylistFragmentArgs by navArgs()
	private lateinit var playlist: StagedData
	private lateinit var binding: FragmentPlaylistBinding
	private lateinit var innerVideosAdapter: InnerVideosAdapter
	private lateinit var youTubePlayerInstance: YouTubePlayer
	private val playlistViewModel: PlaylistViewModel by viewModels()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		innerVideosAdapter = InnerVideosAdapter(this)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentPlaylistBinding.inflate(inflater, container, false)
		lifecycle.addObserver(binding.youtubePlayerView)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initialSetup()
		renderState()
		bookmarkClicked()
	}

	private fun initialSetup() {
		setupInnerVideosContainer()
		playlist = playlistFragmentArgs.recentPlayList
		val videos = playlist.relatedVideos
		val playingVideo = playlist.playingVideo
		innerVideosAdapter.submitList(videos)
		setupViews(playlist.playingVideo)
		firstSetupSelectedVideo(playingVideo.videoId)
		checkIfVideoSaved()
	}

	private fun firstSetupSelectedVideo(videoId: String) {
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
		if (::youTubePlayerInstance.isInitialized){
			youTubePlayerInstance.loadVideo(videoId, 0f)
		}
		else {
			Log.w("TAG", "YouTube Player is not ready yet")
			Toast.makeText(requireContext(), "YouTube Player is not ready yet", Toast.LENGTH_SHORT).show()
		}
	}

	private fun setupViews(video: Video) {
		with(binding) {
			tvPlayingVideoTitle.text = video.title
			tvPlayingVideoCategory.text = video.subCategory
		}
	}

	private fun setupInnerVideosContainer() {
		binding.rvVideosContainerInsidePlaylist.apply {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(requireContext())
			adapter = innerVideosAdapter
		}
	}

	private fun bookmarkClicked() {
		binding.icSave.setOnClickListener {
			sendSaveVideoIntent()
//			sendDeleteVideoIntent()
			//if video not saved so save it
			//else if video saved so delete it send delete intent
		}
	}

	private fun sendSaveVideoIntent() {
		lifecycleScope.launch {
			playlistViewModel.userIntentChannel.send(PlaylistIntent.SaveVideo(playlist.playingVideo))
		}
	}

	private fun renderState() {
		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				playlistViewModel.state.collect { state ->
					when (state) {
						is PlaylistState.Success -> {
							fillBookmarkIcon()
							playlistViewModel.clearState()
						}

						is PlaylistState.Failure -> {
							unFillBookmarkIcon()
							playlistViewModel.clearState()
						}

						PlaylistState.Idle       -> {}

						PlaylistState.Loading    -> {}
					}
				}
			}
		}
	}

	private fun checkIfVideoSaved() {
		lifecycleScope.launch {
			playlistViewModel.userIntentChannel.send(PlaylistIntent.GetSavedVideoById(playlist.playingVideo.videoId))
		}
	}

	private fun sendDeleteVideoIntent(){
		lifecycleScope.launch{
			playlistViewModel.userIntentChannel.send(PlaylistIntent.DeleteVideo(playlist.playingVideo))
		}
	}

	private fun fillBookmarkIcon() {
		binding.icSave.setImageResource(R.drawable.bookmark_24px_fill)
	}

	private fun unFillBookmarkIcon() {
		binding.icSave.setImageResource(R.drawable.bookmark_24px_empty)
	}

	override fun videoClicker(video: Video) {
		playSelectedVideo(video.videoId)
		setupViews(video)
		//change playlist playing video
		playlist = playlist.copy(playingVideo = video)
		checkIfVideoSaved()
	}
}