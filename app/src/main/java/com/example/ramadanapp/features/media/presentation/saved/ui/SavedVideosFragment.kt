package com.example.ramadanapp.features.media.presentation.saved.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramadanapp.R
import com.example.ramadanapp.common.presentation.showSnackbar
import com.example.ramadanapp.databinding.FragmentSavedVideosBinding
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.media.presentation.saved.mvi.SavedScreenIntents
import com.example.ramadanapp.features.media.presentation.saved.mvi.SavedVideosScreenState
import com.example.ramadanapp.features.media.presentation.saved.mvi.SavedVideosViewModel
import com.example.ramadanapp.features.media.presentation.saved.ui.adapters.SavedVideosAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedVideosFragment : Fragment(), SavedVideosAdapter.SavedVideoClicked {
	private lateinit var binding: FragmentSavedVideosBinding
	private val savedVideosViewModel: SavedVideosViewModel by viewModels()
	private lateinit var savedVideosAdapter: SavedVideosAdapter
	private lateinit var youTubePlayerListener: YouTubePlayer
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		savedVideosAdapter = SavedVideosAdapter(this)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentSavedVideosBinding.inflate(inflater, container, false)
		lifecycle.addObserver(binding.savedYoutubePlayerView)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		renderState()
		setupSavedVideosContainer()
		sendLoadSavedVideosIntent()
		setupYoutubePlayer()
	}

	private fun sendLoadSavedVideosIntent() {
		lifecycleScope.launch {
			savedVideosViewModel.userIntentChannel.send(SavedScreenIntents.LoadSavedVideos)
		}
	}

	private fun renderState() {
		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				savedVideosViewModel.state.collect { state ->
					when (state) {
						is SavedVideosScreenState.Failure -> {
							showSnackbar(state.error.toString())
							binding.savedVideosLoader.visibility = View.GONE
						}
						SavedVideosScreenState.Idle       -> {}
						SavedVideosScreenState.Loading    -> {
							binding.savedVideosLoader.visibility = View.VISIBLE
						}
						is SavedVideosScreenState.Success -> {
							val videos = state.videos
							if (videos.isEmpty()) {
								//setup emptyView if there is no saved videos
								binding.emptySavedVideosView.visibility = View.VISIBLE
							}
							savedVideosAdapter.submitList(videos)
							binding.savedVideosLoader.visibility = View.GONE
						}
					}
				}
			}
		}
	}

	private fun setupSavedVideosContainer() {
		binding.rvSavedVideos.apply {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(requireContext())
			adapter = savedVideosAdapter
		}
	}

	private fun setupYoutubePlayer() {
		binding.savedYoutubePlayerView.addYouTubePlayerListener(object :
			AbstractYouTubePlayerListener() {
			override fun onReady(youTubePlayer: YouTubePlayer) {
				super.onReady(youTubePlayer)
				youTubePlayerListener = youTubePlayer
			}
		})
	}

	private fun playVideo(video: Video) {
		binding.savedYoutubePlayerView.visibility = View.VISIBLE
		if (::youTubePlayerListener.isInitialized) {
			youTubePlayerListener.loadVideo(video.videoId, 0f)
		} else {
			Toast.makeText(requireContext(), "Wait a moment", Toast.LENGTH_SHORT).show()
		}
	}

	private fun sendSaveLastVideoIntent(video:Video){
		lifecycleScope.launch{
			savedVideosViewModel.userIntentChannel.send(SavedScreenIntents.SaveLastSeenVideo(video))
		}
	}
	override fun onSavedVideoClicked(video: Video) {
		playVideo(video)
		sendSaveLastVideoIntent(video)
	}
}