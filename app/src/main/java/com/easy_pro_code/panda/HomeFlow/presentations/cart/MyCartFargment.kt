package com.easy_pro_code.panda.HomeFlow.presentations.cart

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.BuildConfig.MAPS_API_KEY
import com.easy_pro_code.panda.HomeFlow.models.MyCartModel
import com.easy_pro_code.panda.HomeFlow.view_model.CreateAddressViewModel
import com.easy_pro_code.panda.HomeFlow.view_model.GetCartViewModel
import com.easy_pro_code.panda.HomeFlow.view_model.OrdersViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.databinding.FragmentCartBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyCartFargment : Fragment() {

    private var data: Place? = null
    private var city:String=""
    private lateinit var  binding: FragmentCartBinding
    private lateinit var getAllCartViewModel : GetCartViewModel
    private lateinit var createCartViewModel : OrdersViewModel
    private var cartList : List<MyCartModel> = listOf()
    val sessionManager = AuthUtils.manager
    private lateinit var edTextObj: EditText
    private var edTextId:Int=-1

    var list: List<MyCartModel>? = listOf()
    lateinit var navBar: BottomNavigationView

    val createAddressViewModel:CreateAddressViewModel by activityViewModels()
    var total=0
    var totalAfterDiscount =0

    private lateinit var  cartModel :MyCartModel

    var startAutocompleteIntentListener =
        View.OnClickListener { view: View ->
            edTextObj= view as EditText
            startAutocompleteIntent()
        }

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
        initPlacesSdk()
        binding.deliverToValue.setOnClickListener(startAutocompleteIntentListener)


        if (AuthUtils.manager.getCartId() == null){
            Toast.makeText(requireContext(),"Please, Add Item First",Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }else {
            subscribeToLiveData(cartAdapter)
            getAllCartViewModel.getAllCarts()

            binding.checkOutBtn.setOnClickListener {
                createCartViewModel.createOrder()
                createCartViewModel.createOrderLiveData.observe(viewLifecycleOwner) {
                    if (it?.success.toString().equals("order is done")) { Toast.makeText(requireContext(), "Order Add Successfully :)", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Sorry, Failed to Create Order! :(", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }


        binding.applyBtn.setOnClickListener {
            if (binding.enterPromoCode.getText().toString().equals("panda20")){
                binding.promoCodeActiveted.visibility = View.VISIBLE
                totalAfterDiscount = (total*0.8).toInt()
                binding.totalPrice.setText("YER "+totalAfterDiscount.toString()+".00")
            }
        }

        binding.addACreditCart.setOnClickListener {
            Toast.makeText(requireContext(), "Soon :)", Toast.LENGTH_SHORT).show()
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
                        list?.forEach {
                            total += it.price!!
                            Log.e("Ziad Total",total.toString())
                        }
                        binding.subtotalPrice.setText(total.toString())
                    }
                    cartAdapter.submitList(list)
                    navBar = requireActivity().findViewById(R.id.bottom_nav)
                    navBar.getOrCreateBadge(R.id.cart).number = list?.size!!


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
    private fun startAutocompleteIntent(){
        val fields = listOf(
            Place.Field.ADDRESS_COMPONENTS,
            Place.Field.LAT_LNG,
            Place.Field.VIEWPORT,
            Place.Field.NAME,
            Place.Field.ADDRESS)
        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields )
            .build(requireActivity())
        registerForActivityResult.launch(intent)
    }
    private val registerForActivityResult=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
//        viewBinding.edFrom.setOnClickListener(startAutocompleteIntentListener)
        hideSuspendWindow()
        if(result.resultCode == Activity.RESULT_OK ){
            data= result?.data?.let { Autocomplete.getPlaceFromIntent(it) }
            Log.i("PLACE: ", data?.address.toString())
            val address=data?.address.toString().replace(" ","").split(",")
            val country= address[address.size-1]
            Log.i("city: ", city)
            if (this.city==city && country=="Egypt"){
                changeEditTextDataAndTripPoints(data)
                val latLng=data?.latLng
                createAddressViewModel.checkAddress(
                    lat = latLng?.latitude.toString(),
                    lng = latLng?.longitude.toString(),
                    address = data?.name.toString()
                )
            }
        }
    }
    private fun hideSuspendWindow(){
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
    private fun changeEditTextDataAndTripPoints(data: Place?) {
        edTextId=edTextObj.id
        edTextObj.setText(data?.name.toString())
    }
    private fun initPlacesSdk() {
        // Initialize the SDK
        Places.initialize(requireContext().applicationContext,MAPS_API_KEY)
        // Create a new PlacesClient instance
        Places.createClient(requireContext())
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





