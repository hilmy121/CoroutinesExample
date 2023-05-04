package com.example.coroutinesexample.ViewModel

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutinesexample.API.DataModel.ImgFlipResponse
import com.example.coroutinesexample.API.RESTApiObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){

    private val responseList = MutableLiveData<ImgFlipResponse>()

    internal fun getMemes():LiveData<ImgFlipResponse>{
        GlobalScope.launch(Dispatchers.IO) {
//            responseList.postValue(getResponse())
            RESTApiObject().RESTApiService.getMemes().enqueue(object : Callback<ImgFlipResponse>{
                override fun onResponse(call: Call<ImgFlipResponse>, response: Response<ImgFlipResponse>) {

                    response.body().let { responseBody ->
                        if (responseBody != null) {
                            runBlocking {
                                getImgFlipJob(responseBody,responseList).join()
                            }
                        }
                    }


                    GlobalScope.launch(Dispatchers.IO) {
                        response.body().let { body ->
                            if (body != null) {
                                Log.i("Your data is processed in", Thread.currentThread().name)
                                responseList.postValue(body)
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




    //Coroutines
    private fun getImgFlipJob(
        response: ImgFlipResponse,
        responseMutableLiveData: MutableLiveData<ImgFlipResponse>
    ):Job{
        return GlobalScope.launch(Dispatchers.IO) {
            response.let {
                Log.i("Your data is processed in", Thread.currentThread().name)
                responseMutableLiveData.postValue(response)
            }
        }
    }

}