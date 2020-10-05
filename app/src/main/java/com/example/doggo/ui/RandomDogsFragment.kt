package com.example.doggo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.doggo.R
import com.example.doggo.adapters.ClickListener
import com.example.doggo.adapters.DogAdapter
import com.example.doggo.viewmodel.RandomViewModel
import kotlinx.android.synthetic.main.fragment_random_dogs.*
import java.util.regex.Pattern

/**
 * A simple [Fragment] subclass.
 */
class RandomDogsFragment : Fragment(), ClickListener, SwipeRefreshLayout.OnRefreshListener {

    //vars
    private val viewModel by navGraphViewModels<RandomViewModel>(R.id.bottom_nav)
    private lateinit var recyclerView: RecyclerView
    private lateinit var dogAdapter: DogAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_random_dogs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialize layout
        swipeRefresh = layout_refresh
        //add listener
        swipeRefresh.setOnRefreshListener(this)
        //call method
        initRecyclerView()
        //restore saved recyclerView instance
        if (viewModel.state != null) {
            recyclerView.layoutManager?.onRestoreInstanceState(viewModel.state)
            Toast.makeText(
                requireContext(),
                "Dogs reloaded...",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            viewModel.getRandomUrls()
        }
        //called method
        observeImageUrls()
    }
    //observe the viewModel data/observe the dogImageUrls list
    private fun observeImageUrls() {
        viewModel.dogImageUrls.observe(viewLifecycleOwner, Observer { imageUrls ->
            imageUrls?.let {
                //update the adapterList
                dogAdapter.updateList(imageUrls)
            }
        })
    }
    //initialize recyclerView
    private fun initRecyclerView() {
        recyclerView = recycler_view
        //initialize adapter and take an empty arrayList, which is updated through an observer
        dogAdapter = DogAdapter(arrayListOf(), this)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = dogAdapter
           recyclerView.setHasFixedSize(true)
        }
    }
    //interface OnClick method/get dogBreed name
    override fun getBreedInformation(adapterListData: String) {
        //Extracting dogBreed names from the adapterList
        val pattern = Pattern.compile("https://images.dog.ceo/breeds/(\\w+)")
        val matcher = pattern.matcher(adapterListData)
        if (matcher.find()) {
            Toast.makeText(
                requireContext(),
                "This breed is called ${matcher.group(1).toUpperCase()}.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(requireContext(), "Dog breed not found", Toast.LENGTH_SHORT).show()
        }
    }
    //onSwipeRefresh method implementation
    override fun onRefresh() {
        viewModel.getRandomUrls()
        observeImageUrls()
        Toast.makeText(
            requireContext(),
            "Watch out, cute doggoes coming in...",
            Toast.LENGTH_SHORT
        ).show()
        swipeRefresh.isRefreshing = false
    }
    //saving the state of the recyclerView in onPause and retrieving it later in OnViewCreated
    override fun onPause() {
        super.onPause()
        viewModel.state = recyclerView.layoutManager?.onSaveInstanceState()
    }
}