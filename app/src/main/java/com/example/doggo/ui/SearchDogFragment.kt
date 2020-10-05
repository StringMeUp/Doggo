package com.example.doggo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doggo.R
import com.example.doggo.adapters.BreedsAdapter
import com.example.doggo.databinding.FragmentSearchDogBinding
import com.example.doggo.viewmodel.SearchBreedsViewModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class SearchDogFragment : Fragment(), AdapterView.OnItemSelectedListener {

    //vars
    private lateinit var arrayAdapter: ArrayAdapter<CharSequence>
    private lateinit var viewModel: SearchBreedsViewModel
    private lateinit var binding: FragmentSearchDogBinding
    private lateinit var breedsAdapter: BreedsAdapter
    private lateinit var manager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the dog_card for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_dog, container, false)
        //initialize viewModel
        viewModel = ViewModelProvider(this).get(SearchBreedsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialize adapter
        breedsAdapter = BreedsAdapter(arrayListOf())
        manager = LinearLayoutManager(requireContext())

        binding.recyclerViewBreeds.apply {
            adapter = breedsAdapter
            layoutManager = manager
            binding.recyclerViewBreeds.setHasFixedSize(true)
        }

        //find string array
        arrayAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.allBreeds,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //set adapter to spinner
            binding.spinnerAllBreeds.adapter = adapter
            //set onItemSelected listener
            binding.spinnerAllBreeds.onItemSelectedListener = this
        }
    }

    //implemented onItemSelectedListener methods
    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(
            requireContext(),
            "Please select a breed.", Toast.LENGTH_SHORT
        ).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(
            requireContext(),
            "You have selected: ${parent?.getItemAtPosition(position).toString()
                .toUpperCase(Locale.ROOT)+"."}", Toast.LENGTH_SHORT
        ).show()
        binding.viewModel?.fetchBreed(parent?.getItemAtPosition(position).toString())

        binding.viewModel?.requestedBreed?.observe(viewLifecycleOwner, Observer { urls ->
            urls?.let {
                breedsAdapter.update(urls)
            }
        })
    }
}