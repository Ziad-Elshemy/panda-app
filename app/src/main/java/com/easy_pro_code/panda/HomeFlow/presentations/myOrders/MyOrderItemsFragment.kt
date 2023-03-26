package com.easy_pro_code.panda.HomeFlow.presentations.myOrders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.HomeFlow.models.Order
import com.easy_pro_code.panda.HomeFlow.models.OrderItems
import com.easy_pro_code.panda.HomeFlow.view_model.OrdersViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.remote_backend.OrderItemsItem
import com.easy_pro_code.panda.databinding.FragmentMyOrderItemsBinding


class MyOrderItemsFragment : Fragment() {

//    lateinit var ordersViewModel: OrdersViewModel
//
//    lateinit var recyclerView:RecyclerView
//
//    var list:List<Order>?= listOf()
//
//    var orderList = hashMapOf<String,List<OrderItemsItem?>>()
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        ordersViewModel =ViewModelProvider(this).get(OrdersViewModel::class.java)
//    }
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_orders,container,false)
//        val adapter = MyOrdersAdapter(
//            ReviewButtonClickListener {
//            Toast.makeText(requireContext(),"Coming Soon",Toast.LENGTH_LONG).show()
//        },list!!)
//
//
//
//        binding.ordersRv.adapter=adapter
//
//        adapter.submitList(list)

//        ordersViewModel.orderLiveData.observe(viewLifecycleOwner)
//        { it ->
//
//            it?.orders.let {
//                    response ->
//                val orders= mutableListOf<Order>()
//                 response?.map {
//                    orderItem->
//                     orderItem?.cart?.items?.map {
//                         productItemResponse->
//                         orders.add(
//                             Order(
//                                 id = productItemResponse?.productId?.id.toString(),
//                                 imageId = productItemResponse?.productId?.image.toString(),
//                                 name = productItemResponse?.productId?.title.toString(),
//                                 price = productItemResponse?.productId?.price.toString(),
////                         imageId = itemsItem?.productId?.image.toString(),
////                        name = itemsItem?.productId?.title.toString(),
////                        price = itemsItem?.productId?.price.toString(),
//                                 completed = "pending"
//                             )
//                         )
//
//                     }
//            }
//                orderList=orders.toList()
//
//
//                binding.count.text =  "(${orderList?.size.toString()} items)"
//            }
//            adapter.submitList(orderList)
//
//
//
//        }


    lateinit var binding: FragmentMyOrderItemsBinding

    var list:List<Order>?= listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
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


//it.orders?.map {
//    itemsItem ->
//    list = itemsItem?.cart?.items?.map {
//        it?.productId.let {
//            Order(
//                id = it?.id.toString(),
//                imageId = it?.image.toString(),
//                name = it?.title.toString(),
//                price = it?.price.toString(),
////                         imageId = itemsItem?.productId?.image.toString(),
////                        name = itemsItem?.productId?.title.toString(),
////                        price = itemsItem?.productId?.price.toString(),
//                completed = "pending"
//            )
//        }
//    }
//}