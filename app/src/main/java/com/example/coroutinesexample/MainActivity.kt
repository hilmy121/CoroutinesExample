package com.example.coroutinesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinesexample.ViewModel.Adapter.MainAdapter
import com.example.coroutinesexample.ViewModel.MainViewModel
import com.example.coroutinesexample.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
    private fun setupUI(){
        mainViewBinding.rvMain.setHasFixedSize(true)
        mainViewBinding.rvMain.layoutManager = LinearLayoutManager(this)
        mainViewBinding.rvMain.adapter = mainAdapter

    }
}