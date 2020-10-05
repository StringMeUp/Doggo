package com.example.doggo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doggo.model.modelrandom.RandomImages
import com.example.doggo.networking.DogApiInterface
import com.example.doggo.util.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchBreedsViewModel : ViewModel() {

    val requestedBreed = MutableLiveData<ArrayList<String>>()
    private val builder = ServiceBuilder().buildConnection(DogApiInterface::class.java)

    fun fetchBreed(breed: String) {
        val callMyBreed =
            builder.getBreed(puggle = breed)
        callMyBreed.enqueue(object : Callback<RandomImages> {
            override fun onFailure(call: Call<RandomImages>, t: Throwable) {
                println("This call has failed ${t.printStackTrace()}")
            }

            override fun onResponse(call: Call<RandomImages>, response: Response<RandomImages>) {
                if (!response.isSuccessful) {
                    println("The call was unsuccessful ${response.code()}")
                } else {
                    val myBreed = response.body()
                    if (myBreed != null) {
                        requestedBreed.value = ArrayList<String>(myBreed.message)
                    }
                }
            }
        })
    }
}