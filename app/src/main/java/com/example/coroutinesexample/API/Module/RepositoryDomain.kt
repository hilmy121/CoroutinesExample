package com.example.coroutinesexample.API.Module

import android.app.Application
import com.example.coroutinesexample.API.DataModel.ImgFlipResponse
import com.example.coroutinesexample.API.RESTApiEndpoint
import com.example.coroutinesexample.API.RESTApiObject
import javax.inject.Inject

class RepositoryDomain @Inject constructor(
    private val api:RESTApiEndpoint,
    private val appContext:Application
):Repository {


    override suspend fun getMemesCall():ImgFlipResponse {
        val response = RESTApiObject.providesRetrofitClient().getAwaitMemes()
        var result:ImgFlipResponse?=null
        if (response.isSuccessful){
            response.body().let {
                if (it!=null){
                    result=it
                }
            }
        }
        return result!!
    }
}