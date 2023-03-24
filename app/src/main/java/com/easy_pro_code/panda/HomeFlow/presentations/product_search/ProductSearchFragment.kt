package com.easy_pro_code.panda.HomeFlow.presentations.product_search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.HomeFlow.models.fromProductToProduct
import com.easy_pro_code.panda.HomeFlow.view_model.SearchViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentProductSearchBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductSearchFragment:Fragment() {
    private lateinit var viewBinding:FragmentProductSearchBinding
    private lateinit var viewModel:SearchViewModel
    private var products:List<Product>?= listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_product_search,container,false)
        viewBinding.txtSearch.doOnTextChanged { text, start, before, count ->
            viewModel.searchForProduct(text.toString())
            Log.i("products  in  search",text.toString())
        }
        val adapter=ProductSearchRecyclerViewAdapter(products)
        viewBinding.rvProductsSearch.adapter=adapter
        setAdapterClickListener(adapter)
        subscrbeToLiveData(adapter)
        return viewBinding.root
    }

    private fun setAdapterClickListener(adapter: ProductSearchRecyclerViewAdapter) {
        adapter.onProductSearchClickListener=object :ProductSearchRecyclerViewAdapter.OnProductSearchClickListener{
            override fun onClick(product: Product) {
                findNavController().navigate(
                    ProductSearchFragmentDirections.actionProductSearchFragmentToProductPageFragment(product,null)
                )
            }

        }
    }

    private fun subscrbeToLiveData(adapter: ProductSearchRecyclerViewAdapter) {
        viewModel.liveData.observe(viewLifecycleOwner){
            products=it.products.fromProductToProduct()
            Log.i("products  in  search",products.toString())
            adapter.submitList(products)
        }
    }


}