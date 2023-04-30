package com.example.coroutinesexample.API

import com.example.coroutinesexample.API.DataModel.ImgFlipResponse
import retrofit2.Call
import retrofit2.http.GET

interface RESTApiEndpoint {
    @GET("get_memes")
    fun getMemes(): Call<ImgFlipResponse>
}