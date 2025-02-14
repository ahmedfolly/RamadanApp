package com.example.shopsphere.common.di

import android.content.Context
import androidx.room.Room
import com.example.shopsphere.common.data.repos.local.AppDatabase
import com.example.shopsphere.common.data.repos.remote.RetrofitProvider
import com.example.shopsphere.common.data.repos.remote.ShopSphereService
import com.example.shopsphere.common.domain.repos.remote.IRemoteProvider
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
	fun provideRemoteProvider(service: ShopSphereService, gson: Gson): IRemoteProvider {
		return RetrofitProvider(service, gson)
	}

	@Provides
	@Singleton
	fun provideShopSphereService(retrofit: Retrofit): ShopSphereService {
		return retrofit.create(ShopSphereService::class.java)
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
			.baseUrl("https://dummyjson.com/")
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