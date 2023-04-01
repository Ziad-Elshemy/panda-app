package com.easy_pro_code.panda.HomeFlow.presentations.categories2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.HomeFlow.models.fromProductToProduct
import com.easy_pro_code.panda.HomeFlow.presentations.categories1.Categories1FragmentDirections
import com.easy_pro_code.panda.HomeFlow.presentations.home.HomeFragmentDirections
import com.easy_pro_code.panda.HomeFlow.presentations.home.ProductsHomeRecyclerView
import com.easy_pro_code.panda.HomeFlow.view_model.CategoryViewModel
import com.easy_pro_code.panda.HomeFlow.view_model.SuspendWindowViewModel
import com.easy_pro_code.panda.HomeFlow.view_model.WishListViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.local_database.WishProduct
import com.easy_pro_code.panda.databinding.FragmentSearchResultsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class CategoriesFragment2 : Fragment() {
    private lateinit var viewBinding:FragmentSearchResultsBinding
    private lateinit var categoryViewModel:CategoryViewModel
    private val suspendWindowViewModel:SuspendWindowViewModel by activityViewModels()
    private lateinit var wishListViewModel: WishListViewModel
    private val productsList= listOf<Product>()
    private var wishList:List<WishProduct>? = listOf()
    private lateinit var category:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryViewModel=ViewModelProvider(this).get(CategoryViewModel::class.java)
        wishListViewModel=ViewModelProvider(this).get(WishListViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_search_results,container,false)
        // Inflate the layout for this fragment
        val adapter=ProductsHomeRecyclerView(productsList, wishList, lifecycleScope)
        viewBinding.categories1Rv.adapter=adapter
        subscribeToLiveData(adapter)
        suspendWindowViewModel.progressBar(true)
        val args:CategoriesFragment2Args by navArgs()
        category=args.category
        categoryViewModel.getProductByCategory(category)
        setAdapterClickListener(adapter)
        viewBinding.editTextSearch.setOnClickListener{
            findNavController().navigate(CategoriesFragment2Directions.actionCategoriesFragment2ToProductSearchFragment())
        }


        return viewBinding.root
    }


    private fun setAdapterClickListener(
        productsAdapter: ProductsHomeRecyclerView)
    {
        productsAdapter.onProductClickListener=object :ProductsHomeRecyclerView.OnProductClickListener{
            override fun onClick(product: Product) {
                findNavController().navigate(
                    CategoriesFragment2Directions.actionCategoriesFragment2ToProductPageFragment(
                    product = product,
                    offer = null,
                    phone = null
                )
                )
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=false

            }

            override fun onCheck(product: Product) {
                wishListViewModel.addToWishList(product)
            }

            override fun onUnCheck(product: Product) {
                wishListViewModel.removeFromWishList(product)
            }

        }
    }

    private fun subscribeToLiveData(adapter: ProductsHomeRecyclerView) {
        categoryViewModel.productsLiveData.observe(viewLifecycleOwner){
            suspendWindowViewModel.progressBar(false)
            adapter.submitList(it.categoryProducts.fromProductToProduct())
        }




        wishListViewModel.wishListLiveData.observe(viewLifecycleOwner){
            wishList=it
        }
    }

}