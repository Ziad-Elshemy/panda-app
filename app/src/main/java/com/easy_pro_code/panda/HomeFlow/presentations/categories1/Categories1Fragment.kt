package com.easy_pro_code.panda.HomeFlow.presentations.categories1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.HomeFlow.models.categoryItemToAllCategoryName
import com.easy_pro_code.panda.HomeFlow.presentations.home.CategoryRecyclerViewAdapter
import com.easy_pro_code.panda.HomeFlow.view_model.Categories1ViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentCategories1Binding
import com.google.android.material.bottomnavigation.BottomNavigationView


class Categories1Fragment : Fragment() {

    private lateinit var binding:FragmentCategories1Binding
    private var categories:List<String>?= listOf()
    private lateinit var viewModel: Categories1ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(Categories1ViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_categories1,container,false)
        val adapter=CategoryRecyclerViewAdapter(categories)
        adapter.onCategoryClickListener=object :CategoryRecyclerViewAdapter.OnCategoryClickListener{
            override fun onClick(category: String) {
                findNavController().navigate(
                    Categories1FragmentDirections.actionCategories1FragmentToCategoriesFragment2(category)
                )
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=false
            }
        }
        binding.categories1Rv.adapter=adapter
        viewModel.getAllCategories()

        subscribeToLiveData(adapter)
        return binding.root
    }

    private fun subscribeToLiveData(adapter: CategoryRecyclerViewAdapter) {
        viewModel.liveData.observe(viewLifecycleOwner){
            Log.e("errrrrrrrrfdsfsf",it.toString())
            categories=it.category.categoryItemToAllCategoryName()
            adapter.submitList(categories)
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=true
    }

}