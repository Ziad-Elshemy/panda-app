package com.easy_pro_code.panda.HomeFlow.presentations.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentEmptyCartBinding


class EmptyCartFragment : Fragment() {

    lateinit var binding:FragmentEmptyCartBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_empty_cart,container,false)


        binding.btnContinueShopping.setOnClickListener {
           findNavController().navigate(EmptyCartFragmentDirections.actionEmptyCartFragmentToHomeFragment())
        }

        return binding.root
    }

}