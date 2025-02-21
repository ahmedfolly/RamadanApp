package com.example.ramadanapp.features.media.presentation.saved.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramadanapp.R
import com.example.ramadanapp.databinding.FragmentSavedVideosBinding
import com.example.ramadanapp.features.media.presentation.saved.mvi.SavedScreenIntents
import com.example.ramadanapp.features.media.presentation.saved.mvi.SavedVideosScreenState
import com.example.ramadanapp.features.media.presentation.saved.mvi.SavedVideosViewModel
import com.example.ramadanapp.features.media.presentation.saved.ui.adapters.SavedVideosAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedVideosFragment : Fragment() {
	private lateinit var binding: FragmentSavedVideosBinding
	private val savedVideosViewModel:SavedVideosViewModel by viewModels()
	private lateinit var savedVideosAdapter : SavedVideosAdapter
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		savedVideosAdapter= SavedVideosAdapter()
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentSavedVideosBinding.inflate(inflater,container,false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		renderState()
		setupSavedVideosContainer()
		sendLoadSavedVideosIntent()
	}
	private fun sendLoadSavedVideosIntent(){
		lifecycleScope.launch{
			savedVideosViewModel.userIntentChannel.send(SavedScreenIntents.LoadSavedVideos)
		}
	}
	private fun renderState(){
		lifecycleScope.launch{
			repeatOnLifecycle(Lifecycle.State.STARTED){
				savedVideosViewModel.state.collect{state->
					when(state){
						is SavedVideosScreenState.Failure ->{}
						SavedVideosScreenState.Idle       -> {}
						SavedVideosScreenState.Loading    -> {}
						is SavedVideosScreenState.Success -> {
							val videos = state.videos
							savedVideosAdapter.submitList(videos)
						}
					}
				}
			}
		}
	}
	private fun setupSavedVideosContainer(){
		binding.rvSavedVideos.apply {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(requireContext())
			adapter = savedVideosAdapter
		}
	}
}