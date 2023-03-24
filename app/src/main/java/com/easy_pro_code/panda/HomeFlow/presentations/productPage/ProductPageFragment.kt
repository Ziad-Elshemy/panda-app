package com.easy_pro_code.panda.HomeFlow.presentations.productPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.easy_pro_code.panda.R
import androidx.lifecycle.ViewModelProvider
import com.easy_pro_code.panda.HomeFlow.view_model.AddCartViewModel
import com.easy_pro_code.panda.databinding.FragmentProductPageBinding
import com.easy_pro_code.panda.data.Models.remote_backend.Cart

class ProductPageFragment:Fragment() {
    lateinit var viewBinding:FragmentProductPageBinding
    private lateinit var  addCartViewModel: AddCartViewModel
    lateinit var cart: Cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addCartViewModel= ViewModelProvider(this).get(AddCartViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_product_page,container,false)
        val args:ProductPageFragmentArgs by navArgs()
        val product=args.product
        val offer=args.offer
        var number = viewBinding.dropdownMenuNumOfItems

        if (product!=null){
            viewBinding.totalPriceET.setText(product.price)
            viewBinding.productDetailsTv.setText(product.title)
            viewBinding.categoryTitleTv.setText(product.category)
            viewBinding.rateText1.setText(product.rate.toString())
            viewBinding.newTotalPriceET.isVisible=false
            viewBinding.pricesLineSeparator1.isVisible=false
            viewBinding.newPriceCurrencyText.isVisible=false

        }else if(offer!=null){
            viewBinding.totalPriceET.setText(offer.product.price)
            viewBinding.newTotalPriceET.setText(offer.newPrice)
            viewBinding.productDetailsTv.setText(offer.product.title)
            viewBinding.categoryTitleTv.setText(offer.product.category)
            viewBinding.rateText1.setText(offer.product.rate.toString())
        }
        return viewBinding.root
    }
}