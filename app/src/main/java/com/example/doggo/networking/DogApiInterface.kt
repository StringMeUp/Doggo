package com.example.doggo.networking

import com.example.doggo.model.more.DogBreed
import com.example.doggo.model.modelrandom.RandomImages
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiInterface {
    @GET("breeds/image/random/10")
    fun getRandomDogs(): Call<RandomImages>

    @GET("breed/{puggle}/images/random/10")
    fun getBreed(
        @Path("puggle") puggle: String
    ): Call<RandomImages>

    @GET("DevTides/DogsApi/master/dogs.json")
    fun getMoreDogs(): Call<List<DogBreed>>
}