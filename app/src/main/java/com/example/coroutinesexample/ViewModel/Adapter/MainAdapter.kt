package com.example.coroutinesexample.ViewModel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.coroutinesexample.API.DataModel.Meme
import com.example.coroutinesexample.R
import com.example.coroutinesexample.databinding.ItemRvBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){
    private var resultList : List<Meme>?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv,parent,false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return resultList?.size?:0
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        resultList?.let {
            holder.bind(it[position])
        }
    }
    fun setList(resultList:List<Meme>){
        this.resultList = resultList
    }
    inner class MainViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val itemViewBinding = ItemRvBinding.bind(itemView)

        fun bind(data:Meme){
            itemViewBinding.tvNamaItemPlaces.text = data.name
            itemViewBinding.tvFeatureItemPlaces.text = data.id
            itemViewBinding.tvDistItemPlaces.text = data.width.toString()
            itemViewBinding.tvAddrItemPlaces.text = data.url
            Glide.with(itemView).load(data.url).transform(CenterCrop(),RoundedCorners(30)).into(itemViewBinding.ivItemPlaces)
        }
    }
}