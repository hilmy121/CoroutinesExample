package com.example.coroutinesexample.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinesexample.API.DataModel.ImgFlipResponse
import com.example.coroutinesexample.API.RESTApiObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel(){

    private val responseList = MutableLiveData<ImgFlipResponse>()

    internal fun getMemes():LiveData<ImgFlipResponse>{
        GlobalScope.launch(Dispatchers.IO) {
//            responseList.postValue(getResponse())
            RESTApiObject().RESTApiService.getMemes().enqueue(object : Callback<ImgFlipResponse>{
                override fun onResponse(
                    call: Call<ImgFlipResponse>,
                    response: Response<ImgFlipResponse>
                ) {
                    response.body().let {
                        if (it != null) {
                            Log.i("Your data is processed in", Thread.currentThread().name)
                            responseList.postValue(it)
                        }
                    }
                }

                override fun onFailure(call: Call<ImgFlipResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
        return responseList
    }




    //Coroutines
    private suspend fun getResponse():ImgFlipResponse{
        var responseFlip : ImgFlipResponse? = null
        RESTApiObject().RESTApiService.getMemes().enqueue(object : Callback<ImgFlipResponse>{
            override fun onResponse(
                call: Call<ImgFlipResponse>,
                response: Response<ImgFlipResponse>
            ) {
                response.body().let {
                    if (it != null) {
                        Log.i("Your data is processed in", Thread.currentThread().name)
                        responseFlip = it
                    }
                }
            }

            override fun onFailure(call: Call<ImgFlipResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        return responseFlip!!

    }

}