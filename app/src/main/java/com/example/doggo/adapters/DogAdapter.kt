package com.example.doggo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doggo.R
import com.example.doggo.databinding.RandomDogImageBinding

class DogAdapter(
    private val adapterUrls: ArrayList<String>,
    private val listener: ClickListener
) :
    RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    class DogViewHolder(
        private val binding: RandomDogImageBinding,
        private val listener: ClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(adapterListData: String) {
            Glide.with(binding.imageViewDog)
                .load(adapterListData)
                .override(500, 500)
                .into(binding.imageViewDog)
            itemView.setOnClickListener {
                listener.getBreedInformation(adapterListData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding: RandomDogImageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.random_dog_image,
            parent,
            false
        )
        return DogViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = adapterUrls.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(adapterUrls[position])
    }

    fun updateList(dogList: ArrayList<String>) {
        adapterUrls.clear()
        adapterUrls.addAll(dogList)
        notifyDataSetChanged()
    }
}

interface ClickListener {
    fun getBreedInformation(adapterListData: String)
}