package com.easy_pro_code.panda.HomeFlow.presentations.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.HomeFlow.models.MyCartModel
import com.easy_pro_code.panda.HomeFlow.view_model.CreateAddressViewModel
import com.easy_pro_code.panda.HomeFlow.view_model.GetCartViewModel
import com.easy_pro_code.panda.HomeFlow.view_model.OrdersViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.databinding.FragmentCartBinding

class MyCartFargment : Fragment() {

    private lateinit var  binding: FragmentCartBinding
    private lateinit var getAllCartViewModel : GetCartViewModel
    private lateinit var createCartViewModel : OrdersViewModel
    private var cartList : List<MyCartModel> = listOf()
    val sessionManager = AuthUtils.manager
    var list: List<MyCartModel>? = listOf()

    val createAddressViewModel:CreateAddressViewModel by activityViewModels()


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
        binding.deliverToValue.setText(createAddressViewModel.deliveryLocation)
        val cartAdapter = CartRecyclerView(cartList)
        binding.mycartsRv.adapter=cartAdapter
        cartAdapter.submitList(cartList)
        if (AuthUtils.manager.getCartId() == null){
            Toast.makeText(requireContext(),"Please, Add Item First",Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }else {
            subscribeToLiveData(cartAdapter)
            getAllCartViewModel.getAllCarts()

            binding.checkOutBtn.setOnClickListener {
                createCartViewModel.createOrder()
                createCartViewModel.createOrderLiveData.observe(viewLifecycleOwner) {
                    if (it?.success.toString().equals("order is done")) {
                        Toast.makeText(
                            requireContext(),
                            "Order Add Successfully :)",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Sorry, Failed to Create Order! :(",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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

                var total=0
                //            Log.e("Ziad Adapter live data",it.toString())
                it.carts.let {
                        cartsListResponse->
                    cartsListResponse?.map {
                            cartsItem ->
                        list = cartsItem?.items?.map {
                                productResponse->
                            val myCartModel = MyCartModel(
                                price = productResponse?.productId?.price?.toInt() ,
                                title = productResponse?.productId?.title.toString(),
                                userId = cartsItem.userId.toString(),
                                image = null,
                                count = productResponse?.number,
                                productId = productResponse?.productId?.id.toString(),
                                cartId = cartsItem.id.toString(),
                                date = cartsItem.date.toString()
                            )
                            total+=productResponse?.productId?.price?.toInt()!!
                            myCartModel
                        }
                    }
                    cartAdapter.submitList(list)
                    Log.e("Ziad Adapter data",list?.size.toString())
                    Log.e("userId" , AuthUtils.manager.fetchData().id.toString())
                    binding.subtotalPrice.setText("YER "+total.toString()+".00")
                    var shipping=0
                    if (total<1000)  {
                        shipping=50* list?.size!!
                        binding.shippingFeePrice.setText("YER "+shipping.toString()+".00")
                    }
                    else binding.shippingFeePrice.setText("free")
                    binding.totalPrice.setText("YER "+(total+shipping).toString()+".00")
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





