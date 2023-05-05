package com.example.coroutinesexample.API

import com.example.coroutinesexample.API.DataModel.ImgFlipResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface RESTApiEndpoint {
    //Alternate Way
    @GET("get_memes")
    fun getMemes(): Call<ImgFlipResponse>

    //Using  Await()
    @GET("get_memes")
    suspend fun getAwaitMemes(): Response<ImgFlipResponse>

}