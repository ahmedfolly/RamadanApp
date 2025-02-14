package com.example.shopsphere.android

import android.app.Application
import android.graphics.Insets.add

import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
class ShopSphereApp: Application(){
	override fun onCreate() {
		super.onCreate()

		// Set this ImageLoader as the default for Coil
	}
}