package com.example.ramadanapp.features.sections.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramadanapp.common.presentation.showSnackbar
import com.example.ramadanapp.databinding.MainFragmentBinding
import com.example.ramadanapp.features.media.domain.model.Video
import com.example.ramadanapp.features.sections.presentation.mvi.MainViewModel
import com.example.ramadanapp.features.sections.presentation.mvi.MainIntents
import com.example.ramadanapp.features.sections.presentation.mvi.SectionsState
import com.example.ramadanapp.features.sections.presentation.ui.adapters.CategoriesAdapter
import com.example.ramadanapp.features.sections.presentation.ui.adapters.SectionsAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
	private lateinit var _binding: MainFragmentBinding
	private val binding
		get() = _binding
	private val mediaViewModel: MainViewModel by viewModels()
	private lateinit var sectionsAdapter: SectionsAdapter
	private lateinit var youTubePlayer: YouTubePlayer
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		sectionsAdapter = SectionsAdapter()
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = MainFragmentBinding.inflate(inflater, container, false)
		lifecycle.addObserver(binding.lastSeenYoutubePlayerView)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mediaIntentRender()
		mediaIntentSend()

		getLastSeenVideoRender()
		getLastSeenVideIntentSend()

		setupPlaylistsContainer()
		setupYoutubePlayer()

		goToSavedVideosScreen()
	}

	private fun mediaIntentSend() {
		lifecycleScope.launch {
			mediaViewModel.userIntentChannel.send(MainIntents.LoadSections)
		}
	}

	private fun mediaIntentRender() {
		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				mediaViewModel.state.collect { mainState ->
					when(mainState){
						is SectionsState.Failure -> {
							showSnackbar(mainState.e.message.toString())
							binding.playlistsLoader.visibility = View.GONE
						}
						SectionsState.Idle       -> {}
						SectionsState.Loading    -> {
							binding.playlistsLoader.visibility = View.VISIBLE
						}
						is SectionsState.Success -> {
							val sections = mainState.sections
							sectionsAdapter.submitList(sections)
							binding.playlistsLoader.visibility = View.GONE

						}
					}
				}
			}
		}
	}

	private fun setupPlaylistsContainer() {
		binding.rvPlayListContainer.apply {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(requireContext())
			adapter = sectionsAdapter
		}
	}

	private fun setupYoutubePlayer() {
		binding.lastSeenYoutubePlayerView.addYouTubePlayerListener(object :
			AbstractYouTubePlayerListener() {
			override fun onReady(youTubePlayer: YouTubePlayer) {
				super.onReady(youTubePlayer)
				this@MainFragment.youTubePlayer = youTubePlayer
				getLastSeenVideoRender() // Ensure we load the video once ready
			}
		})
	}

	private fun playLastSeenVideo(video: Video) {
		if (::youTubePlayer.isInitialized) {
			val videoId =
				if (video.videoId.isEmpty()) "tldDnDX5UKM" else video.videoId //default videoId if there is no last seen video
			Log.d("TAG", "playLastSeenVideo: $videoId")
			youTubePlayer.loadVideo(videoId, 0f)
			youTubePlayer.pause()
		}
	}

	private fun getLastSeenVideoRender() {
		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				mediaViewModel.lastSeenVideoState.collect { lastSeenVideo ->
					Log.d("TAG", "getLastSeenVideoRender: $lastSeenVideo")
					if (lastSeenVideo != null) {
						playLastSeenVideo(lastSeenVideo)
						binding.bannerImage.visibility = View.VISIBLE
					}
				}
			}
		}
	}

	private fun getLastSeenVideIntentSend() {
		lifecycleScope.launch {
			mediaViewModel.userIntentChannel.send(MainIntents.GetLastSeenVideo)
		}
	}

	private fun goToSavedVideosScreen() {
		binding.openSavedVideosScreen.setOnClickListener {
			val fromMainToSavedVideosScreenAction =
				MainFragmentDirections.Companion.actionMainFragmentToSavedVideosFragment()
			findNavController().navigate(fromMainToSavedVideosScreenAction)
		}
	}
}