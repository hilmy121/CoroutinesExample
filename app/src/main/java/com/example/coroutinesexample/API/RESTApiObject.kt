package com.example.coroutinesexample.API

import android.app.Application
import com.example.coroutinesexample.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RESTApiObject : Application() {
    private const val BASE_HTTP_URL2="https://api.imgflip.com/"

    private val RESTApiClient = OkHttpClient().newBuilder().addInterceptor(
        HttpLoggingInterceptor().apply { level = if (com.example.coroutinesexample.BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    private val RetrofitClient = Retrofit.Builder()
        .baseUrl(BASE_HTTP_URL2)
        .client(RESTApiClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesRetrofitClient():RESTApiEndpoint{
        val RESTApiService : RESTApiEndpoint = RetrofitClient.create(RESTApiEndpoint::class.java)
        return RESTApiService
    }

}