package com.easy_pro_code.panda.HomeFlow.presentations.categories2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.HomeFlow.models.fromProductToProduct
import com.easy_pro_code.panda.HomeFlow.presentations.home.ProductsHomeRecyclerView
import com.easy_pro_code.panda.HomeFlow.view_model.CategoryViewModel
import com.easy_pro_code.panda.HomeFlow.view_model.SuspendWindowViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentSearchResultsBinding


class CategoriesFragment2 : Fragment() {
    private lateinit var viewBinding:FragmentSearchResultsBinding
    private lateinit var categoryViewModel:CategoryViewModel
    private val suspendWindowViewModel:SuspendWindowViewModel by activityViewModels()
    private val productsList= listOf<Product>()
    private lateinit var category:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryViewModel=ViewModelProvider(this).get(CategoryViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_search_results,container,false)
        // Inflate the layout for this fragment
        val adapter=ProductsHomeRecyclerView(productsList)
        viewBinding.categories1Rv.adapter=adapter
        subscribeToLiveData(adapter)
        suspendWindowViewModel.progressBar(true)
        val args:CategoriesFragment2Args by navArgs()
        category=args.category
        categoryViewModel.getProductByCategory(category)
        return viewBinding.root
    }

    private fun subscribeToLiveData(adapter: ProductsHomeRecyclerView) {
        categoryViewModel.productsLiveData.observe(viewLifecycleOwner){
            suspendWindowViewModel.progressBar(false)
            adapter.submitList(it.categoryProducts.fromProductToProduct())
        }
    }

}