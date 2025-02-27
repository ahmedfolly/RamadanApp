package com.example.ramadanapp.features.media.presentation.playlist.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramadanapp.R
import com.example.ramadanapp.common.presentation.ui.BaseFragment
import com.example.ramadanapp.databinding.FragmentPlaylistBinding
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.media.presentation.playlist.mvi.PlaylistIntent
import com.example.ramadanapp.features.media.presentation.playlist.mvi.PlaylistState
import com.example.ramadanapp.features.media.presentation.playlist.mvi.PlaylistViewModel
import com.example.ramadanapp.features.media.presentation.playlist.ui.adapters.InnerVideosAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlaylistFragment : BaseFragment<FragmentPlaylistBinding>(
	FragmentPlaylistBinding::inflate
), InnerVideosAdapter.OnVideoClicker {
	private val playlistFragmentArgs: PlaylistFragmentArgs by navArgs()
	private lateinit var innerVideosAdapter: InnerVideosAdapter
	private lateinit var youTubePlayerInstance: YouTubePlayer
	private val playlistViewModel: PlaylistViewModel by viewModels()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		innerVideosAdapter = InnerVideosAdapter(this)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}

	override fun initViews() {
		initialSetup()
		lifecycle.addObserver(binding.youtubePlayerView)
		backBtnClicked()
		shareBtnClicked()
	}

	private fun initialSetup() {
		val categoryName = playlistFragmentArgs.categoryName
		renderState()
		sendLoadSectionVideosIntent(categoryName)
		setupInnerVideosContainer()
		firstSetupSelectedVideo()
		bookmarkClicked()
		collectPlayingVideoData()
		collectSavedVideoState()
	}

	private fun firstSetupSelectedVideo() {
		binding.youtubePlayerView.addYouTubePlayerListener(object :
			AbstractYouTubePlayerListener() {
			override fun onReady(youTubePlayer: YouTubePlayer) {
				super.onReady(youTubePlayer)
				youTubePlayerInstance = youTubePlayer
			}
		})
	}

	private fun playSelectedVideo(videoId: String) {
		if (::youTubePlayerInstance.isInitialized) {
			youTubePlayerInstance.loadVideo(videoId, 0f)
		} else {
			Log.w("TAG", "YouTube Player is not ready yet")
			Toast.makeText(requireContext(), "YouTube Player is not ready yet", Toast.LENGTH_SHORT)
				.show()
		}
	}

	private fun setupViews(video: Video) {
		with(binding) {
			tvPlayingVideoTitle.text = video.title
			tvPlayingVideoCategory.text = video.category
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
			sendSaveOrDeleteIntent()
		}
	}

	private fun checkIfVideoSaved(video: Video) {
		lifecycleScope.launch {
			playlistViewModel.userIntentChannel.send(PlaylistIntent.GetSavedVideoById(video.videoId))
		}
	}

	private fun fillBookmarkIcon() {
		binding.icSave.setImageResource(R.drawable.bookmark_24px_fill)
	}

	private fun unFillBookmarkIcon() {
		binding.icSave.setImageResource(R.drawable.bookmark_24px_empty)
	}

	private fun collectPlayingVideoData() {
		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				playlistViewModel.playingVideoState.collect { video ->
					if (video != null) {
						setupViews(video)
						checkIfVideoSaved(video)
					}
				}
			}
		}
	}

	private fun sendSaveLastSeenVideoIntent(video: Video) {
		lifecycleScope.launch {
			playlistViewModel.userIntentChannel.send(PlaylistIntent.SaveLastSeenVideo(video))
		}
	}

	private fun sendSaveOrDeleteIntent() {
		lifecycleScope.launch {
			playlistViewModel.playingVideoState.value?.let {
				playlistViewModel.userIntentChannel.send(PlaylistIntent.SaveOrDeleteVideo(it))
			}
		}
	}

	private fun collectSavedVideoState() {
		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				playlistViewModel.savedVideoState.collect { isSaved ->
					if (isSaved) {
						fillBookmarkIcon()
					} else {
						unFillBookmarkIcon()
					}
				}
			}
		}
	}

	private fun renderState() {
		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				playlistViewModel.state.collect { state ->
					when (state) {
						is PlaylistState.Failure -> {
							showSnackBar(state.error.toString())
							hideLoadingDialog()
						}
						PlaylistState.Idle       -> {}
						PlaylistState.Loading    -> {
							showLoadingDialog()
						}
						is PlaylistState.Success -> {
							innerVideosAdapter.submitList(state.playlist.videos)
							hideLoadingDialog()
						}
					}
				}
			}
		}
	}

	private fun sendLoadSectionVideosIntent(categoryName: String) {
		lifecycleScope.launch {
			playlistViewModel.userIntentChannel.send(PlaylistIntent.LoadSectionItems(categoryName))
		}
	}

	private fun backBtnClicked(){
		binding.backToMain.setOnClickListener{
			backToMain()
		}
	}
	private fun shareUrl(url: String) {
		val shareIntent = Intent(Intent.ACTION_SEND).apply {
			type = "text/plain"
			putExtra(Intent.EXTRA_TEXT, url)
		}
		startActivity(Intent.createChooser(shareIntent, "Share via"))
	}
	private fun shareBtnClicked(){
		binding.icShare.setOnClickListener{
			playlistViewModel.playingVideoState.value?.let {
				shareUrl(it.url)
			}
		}
	}
	override fun videoClicker(video: Video) {
		binding.playingVideoLayout.visibility = View.VISIBLE
		playSelectedVideo(video.videoId)

		playlistViewModel.updateVideoState(video)

		sendSaveLastSeenVideoIntent(video)
	}
}