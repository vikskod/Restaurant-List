package com.vikskod.restaurantlist.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikskod.abbostsfordrestaurant.data.model.RestaurantX
import com.vikskod.restaurantlist.R
import com.vikskod.restaurantlist.databinding.FragmentRestaurantBinding
import com.vikskod.restaurantlist.ui.adapter.RestaurantAdapter
import com.vikskod.restaurantlist.ui.viewmodel.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteRestaurantFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantBinding
    private lateinit var viewModel: RestaurantViewModel

    @Inject
    lateinit var restaurantAdapter: RestaurantAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRestaurantBinding.bind(view)
        viewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)

        initRecyclerView()
        setupObservers()
    }

    private fun initRecyclerView() {
        binding.rvRestaurant.apply {
            adapter = restaurantAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setupObservers() {
        showEmptyMessage(false)
        showProgressBar(true)
        viewModel.getFavouriteRestaurant.observe(viewLifecycleOwner, {
            showProgressBar(false)
            if (!it.isNullOrEmpty()) {
                restaurantAdapter.setAdapter(it as ArrayList<RestaurantX>)
            } else {
                // Data is empty
                showEmptyMessage(true)
                restaurantAdapter.clearAdapter()
            }
        })
    }


    private fun showProgressBar(showProgress: Boolean) {
        if (showProgress)
            binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    private fun showEmptyMessage(show: Boolean) {
        if (show) {
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.tvEmpty.visibility = View.GONE
        }
    }
}