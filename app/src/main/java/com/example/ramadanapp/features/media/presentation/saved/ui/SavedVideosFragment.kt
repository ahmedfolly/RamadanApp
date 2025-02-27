package com.example.ramadanapp.features.media.presentation.saved.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramadanapp.common.presentation.ui.BaseFragment
import com.example.ramadanapp.databinding.FragmentSavedVideosBinding
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.media.presentation.saved.mvi.SavedScreenIntents
import com.example.ramadanapp.features.media.presentation.saved.mvi.SavedVideosScreenState
import com.example.ramadanapp.features.media.presentation.saved.mvi.SavedVideosViewModel
import com.example.ramadanapp.features.media.presentation.saved.ui.adapters.SavedVideosAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedVideosFragment : BaseFragment<FragmentSavedVideosBinding>(
	FragmentSavedVideosBinding::inflate
), SavedVideosAdapter.SavedVideoClicked {
	private val savedVideosViewModel: SavedVideosViewModel by viewModels()
	private lateinit var savedVideosAdapter: SavedVideosAdapter
	private lateinit var youTubePlayerListener: YouTubePlayer
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		savedVideosAdapter = SavedVideosAdapter(this)
	}
	override fun initViews() {
		renderState()
		setupSavedVideosContainer()
		sendLoadSavedVideosIntent()
		setupYoutubePlayer()
		lifecycle.addObserver(binding.savedYoutubePlayerView)
		backBtnClicked()
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
							showSnackBar(state.error.message.toString())
							hideLoadingDialog()
						}

						SavedVideosScreenState.Idle       -> {}
						SavedVideosScreenState.Loading    -> {
							showLoadingDialog()
						}

						is SavedVideosScreenState.Success -> {
							val videos = state.videos
							if (videos.isEmpty()) {
								//setup emptyView if there is no saved videos
								binding.emptySavedVideosView.visibility = View.VISIBLE
							}
							savedVideosAdapter.submitList(videos)
							hideLoadingDialog()
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

	private fun sendSaveLastVideoIntent(video: Video) {
		lifecycleScope.launch {
			savedVideosViewModel.userIntentChannel.send(SavedScreenIntents.SaveLastSeenVideo(video))
		}
	}

	private fun backBtnClicked(){
		binding.backToMain.setOnClickListener{
			backToMain()
		}
	}
	override fun onSavedVideoClicked(video: Video) {
		playVideo(video)
		sendSaveLastVideoIntent(video)
	}
}