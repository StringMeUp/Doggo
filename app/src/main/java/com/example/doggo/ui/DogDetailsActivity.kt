package com.example.doggo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.example.doggo.R
import com.example.doggo.databinding.ActivityDogDetailsBinding
import com.example.doggo.model.more.DogBreed

class DogDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDogDetailsBinding
    val args: DogDetailsActivityArgs by navArgs()
    private lateinit var arguments: DogBreed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dog_details)
        arguments = args.argDetails
        bindArgs(arguments)
    }

    private fun bindArgs(arguments: DogBreed) {
        //set image
        Glide.with(binding.imageViewDogDetails)
            .load(arguments.imageUrl)
            .override(500, 500)
            .into(binding.imageViewDogDetails)
        //set name
        binding.textNameDetails.text = arguments.dogBreed ?: "N/A"
        //set temperament
        binding.textTemperamentDetails.text = arguments.temperament ?: "N/A"
        //set weight
        binding.textWeightDetails.text =
            arguments.weight?.metric.toString().let {
                if (it.isEmpty()) "N/A" else it
            }
        //set height
        binding.textHeightDetails.text =
            arguments.height?.metric.toString().let {
                if (it.isEmpty()) "N/A" else it
            }
        //set bred for
        binding.textBredFor.text =
            arguments.bredFor.toString().let {
                if (it.isEmpty()) "N/A" else it
            }
        //set breed group
        binding.textBreedGroup.text = arguments.breedGroup ?: "N/A"
        //set life span
        binding.textLifeSpan.text = arguments.lifeSpan ?: "N/A"
    }
}