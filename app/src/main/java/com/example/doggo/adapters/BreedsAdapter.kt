package com.example.doggo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doggo.R
import com.example.doggo.databinding.SearchBreedImageBinding
import kotlinx.android.synthetic.main.search_breed_image.view.*

class BreedsAdapter(
    val breedsImage: ArrayList<String>
) :
    RecyclerView.Adapter<BreedsAdapter.BreedsViewHolder>() {

    class BreedsViewHolder(val breedBinding: SearchBreedImageBinding) :
        RecyclerView.ViewHolder(breedBinding.root) {
        fun bind(adapterList: String) {
            Glide.with(breedBinding.imageViewBreed)
                .load(adapterList)
                .override(500, 500)
                .into(breedBinding.imageViewBreed)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedsViewHolder {
        val breedBinding: SearchBreedImageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.search_breed_image,
            parent, false
        )
        return BreedsViewHolder(breedBinding)
    }

    override fun getItemCount(): Int = breedsImage.size

    override fun onBindViewHolder(holder: BreedsViewHolder, position: Int) {
        holder.bind(breedsImage[position])
    }

    fun update(updateList: ArrayList<String>){
        breedsImage.clear()
        breedsImage.addAll(updateList)
        notifyDataSetChanged()
    }
}