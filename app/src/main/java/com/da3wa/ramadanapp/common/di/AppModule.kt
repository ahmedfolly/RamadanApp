package com.da3wa.ramadanapp.common.di

import android.content.Context
import androidx.room.Room
import com.da3wa.ramadanapp.common.data.repos.local.AppDatabase
import com.da3wa.ramadanapp.common.data.repos.remote.RetrofitProvider
import com.da3wa.ramadanapp.common.data.repos.remote.RamadanAppService
import com.da3wa.ramadanapp.common.domain.repos.remote.IRemoteProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	@Provides
	@Singleton
	fun provideRemoteProvider(service: RamadanAppService, gson: Gson): IRemoteProvider {
		return RetrofitProvider(service, gson)
	}

	@Provides
	@Singleton
	fun provideShopSphereService(retrofit: Retrofit): RamadanAppService {
		return retrofit.create(RamadanAppService::class.java)
	}

	@Provides
	@Singleton
	fun provideGson(): Gson {
		return Gson()
	}

	@Provides
	@Singleton
	fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
		return Retrofit.Builder()
			.client(
				okHttpClient
			)
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl("https://raw.githubusercontent.com/")
			.build()
	}

	@Provides
	@Singleton
	fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
		return OkHttpClient.Builder()
			.addInterceptor(interceptor)
			.build()
	}

	@Provides
	@Singleton
	fun provideLoggingInterceptor(): HttpLoggingInterceptor {
		return HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY
		}
	}

	@Provides
	@Singleton
	fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
		return Room.databaseBuilder(
			context,
			AppDatabase::class.java,
			"shopSphereData"
		).build()
	}
}