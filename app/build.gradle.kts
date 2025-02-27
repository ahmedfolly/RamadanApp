plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.parcelize)
	alias(libs.plugins.hilt.android)
	alias(libs.plugins.ksp)
	alias(libs.plugins.navigationSafeArgs)
}

android {
	namespace = "com.edu.da3wa.ramadanapp"
	compileSdk =35

	defaultConfig {
		applicationId = "com.edu.da3wa.ramadanapp"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
	buildFeatures{
		viewBinding = true
	}
}

dependencies {
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.constraintlayout)
	//Gson
	implementation(libs.converter.gson)
	// Navigation Component
	implementation(libs.androidx.navigation.fragment.ktx)
	implementation(libs.androidx.navigation.ui.ktx)
	//recyclerview
	implementation(libs.androidx.recyclerview)
	//retrofit
	implementation(libs.retrofit)
	implementation (libs.okhttp)
	implementation(libs.logging.interceptor)
	implementation (libs.converter.gson)
	//hilt
	implementation(libs.hilt.android)
	ksp(libs.hilt.compiler)
	//coil
	implementation(libs.coil.kt)
	//Room
	implementation(libs.androidx.room.runtime)
	ksp(libs.androidx.room.compiler)
	implementation(libs.room.ktx)
	//youtube player
	implementation (libs.core)

	//datastore and Gson
	implementation(libs.androidx.datastore.preferences)
	implementation(libs.gson) // For Gson (optional)
	implementation(libs.kotlinx.serialization.json)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
}