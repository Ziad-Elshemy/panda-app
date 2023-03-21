package com.easy_pro_code.panda.HomeFlow.presentations.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.HomeFlow.models.fromWishProductToProduct
import com.easy_pro_code.panda.HomeFlow.presentations.home.ProductsHomeRecyclerView
import com.easy_pro_code.panda.HomeFlow.view_model.WishListViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentWishListBinding


class WishListFragment : Fragment() {
    private lateinit var viewBinding:FragmentWishListBinding
    private lateinit var wishListViewModel: WishListViewModel
    private val wishList:List<Product> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wishListViewModel=ViewModelProvider(this).get(WishListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_wish_list,container,false)
        val wishListAdapter=ProductsHomeRecyclerView(wishList)
        subscribeToLiveData(wishListAdapter)
        return viewBinding.root
    }

    private fun subscribeToLiveData(wishListAdapter: ProductsHomeRecyclerView) {
        wishListViewModel.wishListLiveData.observe(viewLifecycleOwner){
            wishListAdapter.submitList(it.fromWishProductToProduct())
        }
    }

}