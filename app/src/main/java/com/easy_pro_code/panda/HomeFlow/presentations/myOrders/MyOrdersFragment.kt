package com.easy_pro_code.panda.HomeFlow.presentations.myOrders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.HomeFlow.models.Order
import com.easy_pro_code.panda.HomeFlow.view_model.OrdersViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.remote_backend.ProductId
import com.easy_pro_code.panda.databinding.FragmentMyOrdersBinding


class MyOrdersFragment : Fragment() {

    lateinit var binding:FragmentMyOrdersBinding
    lateinit var ordersViewModel: OrdersViewModel

    lateinit var recyclerView:RecyclerView

    var list:List<Order>?= listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ordersViewModel =ViewModelProvider(this).get(OrdersViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_orders,container,false)
        ordersViewModel.getAllOrders()
        val adapter = MyOrdersAdapter(
            ReviewButtonClickListener {
            Toast.makeText(requireContext(),"Coming Soon",Toast.LENGTH_LONG).show()
        },list!!)


        binding.ordersRv.adapter=adapter

        adapter.submitList(list)

        ordersViewModel.orderLiveData.observe(viewLifecycleOwner)
        {
            it?.let {
                    response ->
                 response.orders?.map {
                    orderItem->
                     it.orders?.map {
                      itemsItem ->
                         list = itemsItem?.cart?.items?.map {
                         Order(
                             id = it?.productId?.id.toString(),
                             imageId = it?.productId?.image.toString(),
                             name = it?.productId?.title.toString(),
                             price = it?.productId?.price.toString(),
//                         imageId = itemsItem?.productId?.image.toString(),
//                        name = itemsItem?.productId?.title.toString(),
//                        price = itemsItem?.productId?.price.toString(),
                             completed = "pending"
                         )

                     }

                }
                adapter.submitList(list)

                Log.i("orderrrrrrrrrrrrrrrrrr",list.toString())
            }
            }



        }
        return binding.root
    }

}