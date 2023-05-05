package com.example.coroutinesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinesexample.API.DataModel.ImgFlipResponse
import com.example.coroutinesexample.API.RESTApiObject
import com.example.coroutinesexample.ViewModel.Adapter.MainAdapter
import com.example.coroutinesexample.ViewModel.MainViewModel
import com.example.coroutinesexample.databinding.ActivityMainBinding
import kotlinx.coroutines.*
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
//        runBlockingTest()
    }

    override fun onStart() {
        super.onStart()
//        testCoroutines()
        getResponseList()
    }
    private fun getResponseList(){
        mainViewModel.getMemesAwait().observe(this, Observer{
            it.let {
                if (it != null) {
                    lifecycleScope.launch(Dispatchers.Main){
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
    private fun setupUI(){
        mainViewBinding.rvMain.setHasFixedSize(true)
        mainViewBinding.rvMain.layoutManager = LinearLayoutManager(this)
        mainViewBinding.rvMain.adapter = mainAdapter

    }


    private fun runBlockingTest(){
        Log.i("Before Running runBlocking",Thread.currentThread().name)
        runBlocking {
            Log.i("Start Running runBlocking",Thread.currentThread().name)
            GlobalScope.launch(Dispatchers.IO) {
                Log.i("Start Running globalScope",Thread.currentThread().name)
                delay(2000)
                Log.i("End Running globalScope",Thread.currentThread().name)
            }
            delay(2000)
            Log.i("End Running runBlocking",Thread.currentThread().name)
        }
        Log.i("After Running runBlocking",Thread.currentThread().name)

    }
}