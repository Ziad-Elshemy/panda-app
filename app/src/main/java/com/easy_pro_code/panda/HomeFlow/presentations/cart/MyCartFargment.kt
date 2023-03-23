package com.easy_pro_code.panda.HomeFlow.presentations.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.easy_pro_code.panda.HomeFlow.models.CartModel
import com.easy_pro_code.panda.HomeFlow.models.toCart
import com.easy_pro_code.panda.HomeFlow.view_model.GetCartViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.databinding.FragmentCartBinding

class MyCartFargment : Fragment() {

    lateinit var binding: FragmentCartBinding
    private lateinit var getAllCartViewModel : GetCartViewModel
    private val cartList : List<CartModel> = listOf()
    val sessionManager = AuthUtils.manager
   private lateinit var  cartModel :CartModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAllCartViewModel =ViewModelProvider(this).get(GetCartViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart, container, false)

        val cartAdapter = CartRecyclerView(cartList)
        binding.mycartsRv.adapter=cartAdapter
        cartAdapter.submitList(cartList)
        subscribeToLiveData(cartAdapter)
        getAllCartViewModel.getAllCarts()

    return binding.root
    }

    private fun subscribeToLiveData(cartAdapter:CartRecyclerView){
        getAllCartViewModel.getcartsLiveData.observe(viewLifecycleOwner){
            cartAdapter.submitList(it.carts.toCart())
        }

    }


}





