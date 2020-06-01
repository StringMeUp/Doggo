package com.example.doggo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doggo.model.more.DogBreed
import com.example.doggo.networking.DogApiInterface
import com.example.doggo.util.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreDogsViewModel : ViewModel() {

    val listMoreDogs = MutableLiveData<ArrayList<DogBreed>>()
    private val moreDogsBuilder =
        ServiceBuilder().buildDetailsConnection(DogApiInterface::class.java)

    fun loadMoreDogs() {
        val callMoreDogs = moreDogsBuilder.getMoreDogs()
        callMoreDogs.enqueue(object : Callback<List<DogBreed>> {
            override fun onFailure(call: Call<List<DogBreed>>, t: Throwable) {
                Log.i("ONFAILURE", "The call has failed ${t.printStackTrace()}")
            }

            override fun onResponse(
                call: Call<List<DogBreed>>,
                response: Response<List<DogBreed>>
            ) {
                if (!response.isSuccessful) {
                    Log.i("RESPONSE", "The call was unsuccessful ${response.code()}")
                } else {
                    val moreDogsList = response.body()
                    moreDogsList?.let {
                        val dogsArray = ArrayList<DogBreed>(it)
                        listMoreDogs.value = dogsArray
                    }
                }
            }
        })
    }
}