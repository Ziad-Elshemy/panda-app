package com.easy_pro_code.panda.HomeFlow.presentations.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentCategories1Binding
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