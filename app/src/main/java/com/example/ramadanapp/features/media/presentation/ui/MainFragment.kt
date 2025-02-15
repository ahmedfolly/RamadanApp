package com.example.ramadanapp.features.media.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramadanapp.databinding.MainFragmentBinding
import com.example.ramadanapp.features.media.domain.model.CategorizedPlayList
import com.example.ramadanapp.features.media.presentation.mvi.MediaIntent
import com.example.ramadanapp.features.media.presentation.mvi.MediaState
import com.example.ramadanapp.features.media.presentation.mvi.MediaViewModel
import com.example.ramadanapp.features.media.presentation.ui.adapters.PlayListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
	private lateinit var _binding: MainFragmentBinding
	private val binding
		get() = _binding
	private val mediaViewModel: MediaViewModel by viewModels()
	private lateinit var playlistAdapter: PlayListAdapter
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		playlistAdapter = PlayListAdapter(this)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = MainFragmentBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mediaIntentRender()
		mediaIntentSend()
		setupPlaylistsContainer()
	}
	private fun mediaIntentSend(){
		lifecycleScope.launch{
			mediaViewModel.userIntentChannel.send(MediaIntent.LoadMedia)
		}
	}
	private fun mediaIntentRender(){
		lifecycleScope.launch{
			repeatOnLifecycle(Lifecycle.State.STARTED){
				mediaViewModel.state.collect{mediaState->
					when(mediaState){
						is MediaState.Failure -> {
							Snackbar.make(requireView(),mediaState.e.message.toString(), Snackbar.LENGTH_SHORT).show()
							binding.playlistsLoader.visibility = View.GONE
						}
						is MediaState.Idle       -> {}
						is MediaState.Loading    -> {
							binding.playlistsLoader.visibility = View.VISIBLE
						}
						is MediaState.Success ->{
							val categorizedPlayList = mediaState.media.videos.groupBy {
								it.category
							}.map { CategorizedPlayList(it.key,it.value) }
							playlistAdapter.submitList(categorizedPlayList)
							binding.playlistsLoader.visibility = View.GONE
						}
					}
				}
			}
		}
	}
	private fun setupPlaylistsContainer(){
		binding.rvPlayListContainer.apply {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(requireContext())
			adapter = playlistAdapter
		}
	}
}