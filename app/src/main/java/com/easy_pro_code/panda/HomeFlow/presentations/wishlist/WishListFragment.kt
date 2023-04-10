package com.easy_pro_code.panda.HomeFlow.presentations.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.HomeFlow.models.fromWishProductToProduct
import com.easy_pro_code.panda.HomeFlow.presentations.home.ProductsHomeRecyclerView
import com.easy_pro_code.panda.HomeFlow.view_model.WishListViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.local_database.WishProduct
import com.easy_pro_code.panda.databinding.FragmentWishListBinding


class WishListFragment : Fragment() {
    private lateinit var viewBinding:FragmentWishListBinding
    private lateinit var wishListViewModel: WishListViewModel
    private var wishList:List<WishProduct>? = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wishListViewModel=ViewModelProvider(this).get(WishListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_wish_list,container,false)
        val wishListAdapter=ProductsHomeRecyclerView(
            wishList.fromWishProductToProduct(),
            wishList,
            lifecycleScope
        )
        viewBinding.notificationRv.adapter=wishListAdapter
        setAdapterClickLisenter(wishListAdapter)
        subscribeToLiveData(wishListAdapter)
        return viewBinding.root
    }

    private fun setAdapterClickLisenter(wishListAdapter: ProductsHomeRecyclerView) {
        wishListAdapter.onProductClickListener=object :ProductsHomeRecyclerView.OnProductClickListener{
            override fun onClick(product: Product) {
                findNavController().navigate(
                    WishListFragmentDirections.actionWishListFragmentToProductPageFragment(
                    product = product,
                    offer = null
                )
                )
            }

            override fun onCheck(product: Product) {
                wishListViewModel.addToWishList(product)
            }

            override fun onUnCheck(product: Product) {
                wishListViewModel.removeFromWishList(product)
            }

        }
    }

    private fun subscribeToLiveData(wishListAdapter: ProductsHomeRecyclerView) {
        wishListViewModel.wishListLiveData.observe(viewLifecycleOwner){
            wishList=it
            wishListAdapter.submitList(wishList.fromWishProductToProduct())
            wishListAdapter.submitWishList(wishList)
            viewBinding.count.setText("("+wishList?.size.toString()+" items)")
        }
    }

}