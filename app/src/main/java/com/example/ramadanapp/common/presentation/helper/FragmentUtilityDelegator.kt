package com.example.ramadanapp.common.presentation.helper

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.example.ramadanapp.R

class FragmentUtilityDelegator: IFragmentUtilityDelegator {
	override fun Fragment.showSnackBar(message: String) {
		Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
	}

	override fun Fragment.showToast(message: String) {
		Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
	}

	override fun Context.isInternetAvailable(): Boolean {
		val connectivityManager =
			getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		val network = connectivityManager.activeNetwork ?: return false
		val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

		return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
				capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
	}

	override fun Fragment.createLoadingDialog(): AlertDialog {
		val loadingView =
			LayoutInflater.from(requireContext()).inflate(R.layout.loading_layout,null)
		return createDialog(loadingView)
	}

	override fun Fragment.createNoInternetDialog(): AlertDialog {
		val view = LayoutInflater.from(requireContext()).inflate(R.layout.no_internet_layout,null)
		return createDialog(view)
	}
	private fun Fragment.createDialog(view: View): AlertDialog{
		return AlertDialog.Builder(requireContext())
			.setView(view)
			.setCancelable(false)
			.create()
	}
}