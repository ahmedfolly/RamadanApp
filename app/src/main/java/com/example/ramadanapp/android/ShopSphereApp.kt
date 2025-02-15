package com.example.ramadanapp.android

import android.app.Application

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShopSphereApp: Application(){
	override fun onCreate() {
		super.onCreate()

		// Set this ImageLoader as the default for Coil
	}
}