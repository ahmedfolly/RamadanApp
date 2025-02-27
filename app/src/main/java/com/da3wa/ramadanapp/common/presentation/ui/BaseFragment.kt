package com.da3wa.ramadanapp.common.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.da3wa.ramadanapp.common.presentation.helper.FragmentUtilityDelegator
import com.da3wa.ramadanapp.common.presentation.helper.IFragmentUtilityDelegator
import com.edu.da3wa.ramadanapp.R

abstract class BaseFragment<Binding: ViewBinding> (
	private val inflation:(LayoutInflater,ViewGroup?,Boolean)->Binding
):Fragment(), IFragmentUtilityDelegator by FragmentUtilityDelegator() {
	private var _binding: Binding? = null
	val binding
		get() = _binding ?: throw IllegalStateException("binding not initialized")

	private val loadingDialog by lazy { createLoadingDialog() }
	private val noInternetDialog by lazy { createNoInternetDialog() }

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		_binding = inflation(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initViews()
		checkInternetConnectivity()
	}

	fun checkInternetConnectivity() {
		if (!requireContext().isInternetAvailable()) {
			noInternetDialog.show()
		}
	}

	abstract fun initViews()

	open fun showLoadingDialog(){
		loadingDialog.show()
	}
	open fun hideLoadingDialog(){
		loadingDialog.dismiss()
	}
	open fun backToMain(){
		findNavController().navigate(R.id.global_action_main)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}