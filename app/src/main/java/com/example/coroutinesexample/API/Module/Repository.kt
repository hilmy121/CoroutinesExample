package com.example.coroutinesexample.API.Module

import com.example.coroutinesexample.API.DataModel.ImgFlipResponse

interface Repository {
    suspend fun getMemesCall():ImgFlipResponse
}