package com.example.doggo.viewmodel

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doggo.model.modelrandom.RandomImages
import com.example.doggo.networking.DogApiInterface
import com.example.doggo.util.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RandomViewModel : ViewModel() {
    //viewModel vars
    var state: Parcelable? = null
    //var state uses Parcelable to save the recyclerView state
    val dogImageUrls = MutableLiveData<ArrayList<String>>()
    private val serviceBuilder = ServiceBuilder().buildConnection(DogApiInterface::class.java)

    fun getRandomUrls() {
        val call = serviceBuilder.getRandomDogs()
        call.enqueue(object : Callback<RandomImages> {
            override fun onFailure(call: Call<RandomImages>, t: Throwable) {
                println("The call failed")
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<RandomImages>,
                response: Response<RandomImages>
            ) {
                if (!response.isSuccessful) {
                    println("The call was unsuccessful ${response.code()}")

                } else {
                    val imageUrls = response.body()
                    imageUrls?.let { urls ->
                        val randomDogUrls = urls.message
                        dogImageUrls.value = ArrayList(randomDogUrls)
                    }
                }
            }
        })
    }
}