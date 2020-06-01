package com.example.doggo.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder {
    //RANDOM AND SEARCH DATA RESOURCE
    private val MAIN_BASE_URL: String = "https://dog.ceo/api/"

    val retrofit = Retrofit.Builder()
        .baseUrl(MAIN_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> buildConnection(service: Class<T>): T {
        return retrofit.create(service)
    }

    //MORE DOGS DATA RESOURCE
    private val DETAILS_BASE_URL: String = "https://raw.githubusercontent.com/"

    val detailsRetrofit = Retrofit.Builder()
        .baseUrl(DETAILS_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> buildDetailsConnection(detailsService: Class<T>): T {
        return detailsRetrofit.create(detailsService)
    }
}