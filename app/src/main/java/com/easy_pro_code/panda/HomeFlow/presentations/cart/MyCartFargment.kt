package com.easy_pro_code.panda.HomeFlow.presentations.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.HomeFlow.models.MyCartModel
import com.easy_pro_code.panda.HomeFlow.view_model.GetCartViewModel
import com.easy_pro_code.panda.HomeFlow.view_model.OrdersViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.databinding.FragmentCartBinding

class MyCartFargment : Fragment() {

    lateinit var binding: FragmentCartBinding
    private lateinit var getAllCartViewModel : GetCartViewModel
    private lateinit var createCartViewModel : OrdersViewModel
    private var cartList : List<MyCartModel> = listOf()
    val sessionManager = AuthUtils.manager
    var list: List<MyCartModel>? = listOf()


    private lateinit var  cartModel :MyCartModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAllCartViewModel =ViewModelProvider(this).get(GetCartViewModel::class.java)
        createCartViewModel =ViewModelProvider(this).get(OrdersViewModel::class.java)
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

        binding.checkOutBtn.setOnClickListener {
            createCartViewModel.createOrder()
            createCartViewModel.createOrderLiveData.observe(viewLifecycleOwner){
                if (it?.success.toString().equals("order is done")){
                    Toast.makeText(requireContext(),"Order Add Successfully :)",Toast.LENGTH_SHORT)
                }else{
                    Toast.makeText(requireContext(),"Sorry, Failed to Create Order! :(",Toast.LENGTH_SHORT)
                }
            }
        }

    return binding.root
    }

    private fun subscribeToLiveData(cartAdapter:CartRecyclerView){
        getAllCartViewModel.getcartsLiveData.observe(viewLifecycleOwner){

            if (it.carts!!.isEmpty()){
                findNavController().navigate(MyCartFargmentDirections.actionCartToEmptyCartFragment())
            }
            else{
                //            Log.e("Ziad Adapter live data",it.toString())
                it.carts.let {
                        cartsListResponse->
                    cartsListResponse?.map {
                            cartsItem ->
                        list = cartsItem?.items?.map {
                                productResponse->
                            MyCartModel(
                                price = "YER "+productResponse?.productId?.price.toString()+".00",
                                title= productResponse?.productId?.title.toString(),
                                userId = cartsItem.userId.toString(),
                                image = null,
                                count= 5,
                                productId = productResponse?.productId?.id.toString(),
                                cartId = cartsItem.id.toString(),
                                date = cartsItem.date.toString()
                            )
                        }
                    }
                    cartAdapter.submitList(list)
                    Log.e("Ziad Adapter data",list?.size.toString())
                }
            }
        }
    }


}

//it.let {
//    val list = it.carts?.map {
//        CartModel(
//            price = "YER "+it?.items?.get(0)?.productId?.price.toString()+".00",
//            title= it?.items?.get(0)?.productId?.title.toString(),
//            userId = it?.id.toString(),
//            image = null,
//            count= 5,
//            productId = it?.items?.get(0)?.productId?.id.toString(),
//            cartId = it?.id.toString(),
//            data = it?.date.toString()
//        )
//    }
//    cartAdapter.submitList(list)
//    Log.e("Ziad Adapter data",list.toString())
//}





