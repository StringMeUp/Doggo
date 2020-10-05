package com.example.doggo.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.doggo.model.more.DogBreed
import com.example.doggo.model.more.DogDatabase
import com.example.doggo.networking.DogApiInterface
import com.example.doggo.util.ServiceBuilder
import com.example.doggo.util.SharedPrefsHelper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreDogsViewModel(application: Application) : BaseViewModel(application) {
    val listMoreDogs = MutableLiveData<ArrayList<DogBreed>>()
    val isLoading = MutableLiveData<Boolean>()
    private val sharedHelper = SharedPrefsHelper(application)
    private val refreshTime = 10000000000L //nanoseconds
    private val moreDogsBuilder =
        ServiceBuilder().buildDetailsConnection(DogApiInterface::class.java)

    fun refresh() {
        val updateTime = sharedHelper.retrieveUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        } else {
            loadMoreRemoteDogs()
        }
    }

    private fun loadMoreRemoteDogs() {
        isLoading.value = true
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
                    isLoading.value = false
                    Log.i(
                        "RESPONSE",
                        "The call was unsuccessful ${response.code()}"
                    )
                } else {
                    isLoading.value = false
                    val moreDogsList = response.body()
                    moreDogsList?.let {
                        listMoreDogs.value = ArrayList(it)
                    }
                    storeDogsLocally(moreDogsList)
                    Toast.makeText(
                        getApplication(),
                        "Dogs retrieved from endpoint",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun storeDogsLocally(list: List<DogBreed>?) {
        launch {
            val dao = DogDatabase(getApplication()).dogDao()
            dao.deleteAllDogs()
            list?.let {
                val result = dao.insertAll(*it.toTypedArray())
                var i = 0
                while (i < it.size) {
                    list[i].uuid = result[i].toInt()
                    ++i
                }
                sharedHelper.saveUpdateTime(System.nanoTime())
            }
        }
    }

    private fun fetchFromDatabase() {
        launch {
            val databaseDogs = DogDatabase(getApplication()).dogDao().getAllDogs()
            dogsRetrieved(databaseDogs)
            Toast.makeText(getApplication(), "Dogs retrieved from database", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun dogsRetrieved(list: List<DogBreed>?) {
        list?.let {
            listMoreDogs.value = ArrayList(it)
        }
    }
}