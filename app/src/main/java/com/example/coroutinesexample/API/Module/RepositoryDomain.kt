package com.example.coroutinesexample.API.Module

import android.app.Application
import com.example.coroutinesexample.API.RESTApiEndpoint
import javax.inject.Inject

class RepositoryDomain @Inject constructor(
    private val api:RESTApiEndpoint,
    private val appContext:Application
):Repository {


    override suspend fun getMemesCall() {
        TODO("Not yet implemented")
    }
}