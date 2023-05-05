package com.example.coroutinesexample.ViewModel

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinesexample.API.DataModel.ImgFlipResponse
import com.example.coroutinesexample.API.RESTApiObject
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){

    private val responseList = MutableLiveData<ImgFlipResponse>()

    internal fun getMemes():LiveData<ImgFlipResponse>{
        GlobalScope.launch(Dispatchers.IO) {
            Log.i("Running in ->", Thread.currentThread().name)
            RESTApiObject().RESTApiService.getMemes().enqueue(object : Callback<ImgFlipResponse>{
                override fun onResponse(call: Call<ImgFlipResponse>, response: Response<ImgFlipResponse>) {
                    response.body().let { body ->
                        if (body != null) {
                            GlobalScope.launch {
                                val value = async { body }
                                Log.i("Your data is processed in ->", Thread.currentThread().name)
                                responseList.postValue(value.await())
                            }
                        }
                    }


                }

                override fun onFailure(call: Call<ImgFlipResponse>, t: Throwable) {

                }

            })
        }
        return responseList
    }








}