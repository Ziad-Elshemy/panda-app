package com.easy_pro_code.panda.HomeFlow.presentations.myOrders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.HomeFlow.models.Order
import com.easy_pro_code.panda.HomeFlow.view_model.OrdersViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentMyOrdersBinding


class MyOrdersFragment : Fragment() {

    lateinit var binding:FragmentMyOrdersBinding
    lateinit var ordersViewModel: OrdersViewModel

    lateinit var recyclerView:RecyclerView

    var list:List<Order> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ordersViewModel.getAllOrders()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_orders,container,false)

        val adapter = MyOrdersAdapter(
            ReviewButtonClickListener {
            Toast.makeText(requireContext(),"Coming Soon",Toast.LENGTH_LONG).show()
        },list)

        binding.ordersRv.adapter=adapter

        adapter.submitList(list)

        ordersViewModel.orderLiveData.observe(viewLifecycleOwner)
        {

            val list = it.orders?.map {
                Order(
                    id = it?.id.toString(),
                    imageId = 2,
                    name = "Ziad",
                    price = "59 $",
                    completed = "Completed"

                )

            }
            adapter.submitList(list)

        }
        return binding.root
    }

}