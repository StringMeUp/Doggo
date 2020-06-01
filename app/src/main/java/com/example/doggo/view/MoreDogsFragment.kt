package com.example.doggo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.doggo.R
import com.example.doggo.adapters.MoreAdapter
import com.example.doggo.databinding.FragmentMoreDogsBinding
import com.example.doggo.model.more.DogBreed
import com.example.doggo.viewmodel.MoreDogsViewModel

/**
 * A simple [Fragment] subclass.
 */
class MoreDogsFragment : Fragment(), MoreAdapter.PositionListener {

    private lateinit var moreBinding: FragmentMoreDogsBinding
    private lateinit var viewModel: MoreDogsViewModel
    private lateinit var moreAdapter: MoreAdapter
    private lateinit var staggeredLayoutM: StaggeredGridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        moreBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_more_dogs, container, false)
        viewModel = ViewModelProvider(this).get(MoreDogsViewModel::class.java)
        moreBinding.moreDogsViewModelModel = viewModel
        moreBinding.lifecycleOwner = viewLifecycleOwner
        moreAdapter = MoreAdapter(arrayListOf(), this)
        staggeredLayoutM = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
        Toast.makeText(requireContext(), "Please swipe right.", Toast.LENGTH_LONG).show()
        return moreBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moreBinding.moreDogsViewModelModel?.loadMoreDogs()
        moreBinding.recyclerViewBreeds.apply {
            adapter = moreAdapter
            layoutManager = staggeredLayoutM
            moreBinding.recyclerViewBreeds.setHasFixedSize(true)
        }
        observeList()
    }


    private fun observeList() {
        moreBinding.moreDogsViewModelModel?.listMoreDogs?.observe(
            requireActivity(),
            Observer { breedList ->
                breedList?.let {
                    moreAdapter.updateAdapterList(breedList)
                }
            })
    }

    override fun onPositionClick(adapterListItems: DogBreed) {
        val action = MoreDogsFragmentDirections.actionMoreDogsToDogDetailsActivity(adapterListItems)
        view?.findNavController()?.navigate(action)
    }
}
