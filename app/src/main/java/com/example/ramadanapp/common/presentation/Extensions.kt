package com.example.ramadanapp.common.presentation

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

//show snackbar
fun Fragment.showSnackbar(message:String){
	Snackbar.make(requireView(),message, Snackbar.LENGTH_SHORT).show()
}
