package com.example.coroutinesexample.Service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.coroutinesexample.API.Module.Repository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainService : Service() {

    @Inject
    lateinit var repository:Repository

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}