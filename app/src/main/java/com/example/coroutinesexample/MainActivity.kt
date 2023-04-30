package com.example.coroutinesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinesexample.API.DataModel.ImgFlipResponse
import com.example.coroutinesexample.API.RESTApiObject
import com.example.coroutinesexample.ViewModel.Adapter.MainAdapter
import com.example.coroutinesexample.ViewModel.MainViewModel
import com.example.coroutinesexample.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewBinding: ActivityMainBinding
    private lateinit var mainViewModel:MainViewModel
    private val mainAdapter = MainAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainViewBinding.root)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupUI()
    }

    override fun onStart() {
        super.onStart()
        testCoroutines()
        getResponseList()
    }
    private fun getResponseList(){
        mainViewModel.getMemes().observe(this, Observer {
            it.let {
                if (it != null) {
                    GlobalScope.launch(Dispatchers.Main){
                        Log.i("Activity processing data in ->", Thread.currentThread().name)
                        val dataList = it.data?.memes
                        dataList?.let { memeList ->
                            mainAdapter.setList(memeList)
                            mainAdapter.notifyDataSetChanged()
                        }
                    }

                }
            }
        })
    }
    private fun getCoroutinesList(){
        GlobalScope.launch(Dispatchers.IO) {
            RESTApiObject().RESTApiService.getMemes().enqueue(object : Callback<ImgFlipResponse> {
                override fun onResponse(call: Call<ImgFlipResponse>, response: Response<ImgFlipResponse>) {
                    response.body().let {
                        if (it != null) {
                            Log.i("Your data is processed in", Thread.currentThread().name)
                            val dataList = it.data?.memes
                            dataList?.let { memeList ->
                                Log.i("Your data is displayed in", Thread.currentThread().name)
                                mainAdapter.setList(memeList)
                                mainAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ImgFlipResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
    private fun setupUI(){
        mainViewBinding.rvMain.setHasFixedSize(true)
        mainViewBinding.rvMain.layoutManager = LinearLayoutManager(this)
        mainViewBinding.rvMain.adapter = mainAdapter

    }
    private fun testCoroutines(){
        GlobalScope.launch(Dispatchers.IO) {
            Log.i("Processed in : ",Thread.currentThread().name)

        }
        RESTApiObject().RESTApiService.getMemes().enqueue(object: Callback<ImgFlipResponse>{
            override fun onResponse(call: Call<ImgFlipResponse>, response: Response<ImgFlipResponse>) {
                GlobalScope.launch(Dispatchers.IO){
                    response.body().let {
                            body ->
                        if (body != null) {
                            Log.i("Data is requested in : ",Thread.currentThread().name)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<ImgFlipResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        Log.i("Now Processed in : ",Thread.currentThread().name)
    }
}