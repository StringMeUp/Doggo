package com.example.doggo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        moreBinding.viewModel = viewModel
        moreBinding.lifecycleOwner = viewLifecycleOwner
        moreAdapter = MoreAdapter(arrayListOf(), this)
        staggeredLayoutM = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        return moreBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moreBinding.viewModel?.refresh()
        moreBinding.recyclerViewBreeds.apply {
            adapter = moreAdapter
            layoutManager = staggeredLayoutM
            moreBinding.recyclerViewBreeds.setHasFixedSize(true)
        }
        observeList()
    }

    private fun observeList() {
        moreBinding.viewModel?.listMoreDogs?.observe(
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
