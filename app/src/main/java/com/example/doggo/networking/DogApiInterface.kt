package com.example.doggo.networking

import com.example.doggo.model.more.DogBreed
import com.example.doggo.model.randomSearch.RandomImages
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiInterface {

    //RANDOM DOG IMAGES|https://dog.ceo/api/breeds/image/random/10
    //SPECIFIC/ALL BREEDS IMAGES|https://dog.ceo/api/breed/puggle/images/random/10
    //MORE DOGS/IMAGES + DETAILS|https://raw.githubusercontent.com/DevTides/DogsApi/master/dogs.json

    @GET("breeds/image/random/10")
    fun getRandomDogs(): Call<RandomImages>

    @GET("breed/{puggle}/images/random/10")
    fun getBreed(
        @Path("puggle") puggle: String
    ): Call<RandomImages>

    @GET("DevTides/DogsApi/master/dogs.json")
    fun getMoreDogs(): Call<List<DogBreed>>
}