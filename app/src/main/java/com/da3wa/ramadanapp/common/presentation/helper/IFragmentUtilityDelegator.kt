package com.da3wa.ramadanapp.common.presentation.helper

import android.app.AlertDialog
import android.content.Context
import androidx.fragment.app.Fragment

interface IFragmentUtilityDelegator {
	fun Fragment.showSnackBar(message:String)
	fun Fragment.showToast(message:String)
	fun Context.isInternetAvailable(): Boolean
	fun Fragment.createLoadingDialog(): AlertDialog
	fun Fragment.createNoInternetDialog(): AlertDialog
}