package com.easy_pro_code.panda.HomeFlow.presentations.myOrders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import androidx.navigation.fragment.navArgs
import com.easy_pro_code.panda.HomeFlow.models.Order

import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentMyOrderItemsBinding


class MyOrderItemsFragment : Fragment() {

    lateinit var binding: FragmentMyOrderItemsBinding

    var list:List<Order>?= listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_order_items,container,false)

        val args: MyOrderItemsFragmentArgs by navArgs()
        val orders = args.orderItems

        val adapter = MyOrdersAdapter(
            ReviewButtonClickListener {
            Toast.makeText(requireContext(),"Coming Soon",Toast.LENGTH_LONG).show()
        },orders.items!!)

        binding.ordersRv.adapter=adapter

        adapter.submitList(orders.items)


        return binding.root
    }

}

