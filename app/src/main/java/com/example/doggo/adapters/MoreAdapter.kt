package com.example.doggo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doggo.R
import com.example.doggo.databinding.MoreDogsImageBinding
import com.example.doggo.model.more.DogBreed

class MoreAdapter(val adapterList: ArrayList<DogBreed>, val listener: PositionListener) :
    RecyclerView.Adapter<MoreAdapter.MoreViewHolder>() {

    class MoreViewHolder(
        val binding: MoreDogsImageBinding,
        val listener: PositionListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(adapterListItems: DogBreed) {
            Glide.with(binding.imageViewBreed)
                .load(adapterListItems.imageUrl)
                .override(700, 500)
                .into(binding.imageViewBreed)
            itemView.setOnClickListener {
                listener.onPositionClick(adapterListItems)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreViewHolder {
        val binding: MoreDogsImageBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.more_dogs_image,
                parent,
                false
            )
        return MoreViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = adapterList.size

    override fun onBindViewHolder(holder: MoreViewHolder, position: Int) {
        holder.bind(adapterList[position])
    }

    fun updateAdapterList(updateList: ArrayList<DogBreed>) {
        adapterList.clear()
        adapterList.addAll(updateList)
        notifyDataSetChanged()
    }

    interface PositionListener {
        fun onPositionClick(adapterListItems: DogBreed)
    }
}