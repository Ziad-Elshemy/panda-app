package com.easy_pro_code.panda.HomeFlow.presentations.productPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.easy_pro_code.panda.HomeFlow.view_model.AddCartViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.remote_backend.Cart
import com.easy_pro_code.panda.databinding.FragmentProductPageBinding

class ProductPageFragment:Fragment() {

    lateinit var binding: FragmentProductPageBinding
    private lateinit var  addCartViewModel: AddCartViewModel
    lateinit var cart: Cart
    var number = binding.dropdownMenuNumOfItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addCartViewModel= ViewModelProvider(this).get(AddCartViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_page,container,false)



    }
}