package com.example.coroutinesexample.API

import android.app.Application
import com.example.coroutinesexample.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RESTApiObject : Application() {
    companion object {
        private const val BASE_HTTP_URL2="https://api.imgflip.com/"
    }
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
    val RESTApiService : RESTApiEndpoint = RetrofitClient.create(RESTApiEndpoint::class.java)
}