package com.easy_pro_code.panda.HomeFlow.presentations.myOrders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.HomeFlow.models.OrderItems
import com.easy_pro_code.panda.HomeFlow.view_model.OrdersViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.remote_backend.OrderItemsItem
import com.easy_pro_code.panda.databinding.FragmentMyOrdersBinding


class MyOrdersFragment : Fragment() {

    lateinit var binding:FragmentMyOrdersBinding
    lateinit var ordersViewModel: OrdersViewModel

    lateinit var recyclerView:RecyclerView

    var list:List<String>?= listOf()

     var orderList = hashMapOf<String,List<OrderItemsItem?>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ordersViewModel =ViewModelProvider(this).get(OrdersViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_orders,container,false)
        val adapter = MyOrderItemsAdapter(list!!  ,orderList )

        binding.ordersRv.adapter=adapter

        adapter.submitList(list)


        ordersViewModel.orderLiveData.observe(viewLifecycleOwner)
        {
            it?.orders.let {
                    response ->
                val orders= hashMapOf<String,List<OrderItemsItem?>>()
                response?.map {
                        orderItem->
                    orderItem?.cart?.items?.let { productItemResponse ->
                        orders.put(orderItem.id.toString(), productItemResponse)
                    }
                }
                orderList=orders


                binding.count.text =  "(${orderList?.size.toString()} items)"
            }
            adapter.submitList(orderList.keys.toList())

        }

        adapter.onOrderItemClickListener = object : MyOrderItemsAdapter.OnOrderItemClickListener{
            override fun onClick(order: String) {
                val items = OrderItems(
                    orderList.get(order)
                )
                // todo navigate to the order items fragment
                findNavController().navigate(MyOrdersFragmentDirections.actionMyOrdersFragmentToMyOrderItemsFragment(items))
            }
        }

        ordersViewModel.getAllOrders()
        return binding.root
    }

}
