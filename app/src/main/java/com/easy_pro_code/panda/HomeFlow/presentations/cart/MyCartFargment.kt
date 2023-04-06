package com.easy_pro_code.panda.HomeFlow.presentations.cart

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.BuildConfig.MAPS_API_KEY
import com.easy_pro_code.panda.HomeFlow.models.MyCartModel
import com.easy_pro_code.panda.HomeFlow.view_model.*
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.databinding.FragmentCartBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomnavigation.BottomNavigationView


class MyCartFargment : Fragment() {

    private var currentCart: MyCartModel?=null
    var shipping=0
    private var data: Place? = null
    private var city:String=""
    private lateinit var  binding: FragmentCartBinding
    private lateinit var getAllCartViewModel : GetCartViewModel
    private lateinit var createCartViewModel : OrdersViewModel
    private lateinit var updateCartViewModel : AddCartViewModel
    private var cartList : List<MyCartModel> = listOf()
//    val sessionManager = AuthUtils.manager
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

    private val suspendWindowViewModel: SuspendWindowViewModel by activityViewModels()



    var currentPriceDisplay: TextView?=null
    var currentPrice:Int?=null
    var currentCount:Int?=null
    private var currentProductPrice: Int?=null
    private var currentNumberOfProduct: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAllCartViewModel =ViewModelProvider(this).get(GetCartViewModel::class.java)
        updateCartViewModel =ViewModelProvider(this).get(AddCartViewModel::class.java)
        createCartViewModel =ViewModelProvider(this).get(OrdersViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart, container, false)
        binding.deliverToValue.setText(createAddressViewModel.deliveryLocation)

        val cartAdapter = CartRecyclerView(cartList)
        setupAdapterClickListener(cartAdapter)
        binding.mycartsRv.adapter=cartAdapter
        cartAdapter.submitList(cartList)
        initPlacesSdk()

        binding.deliverToValue.setOnClickListener(startAutocompleteIntentListener)


        if (AuthUtils.manager.getCartId() == null){
            Toast.makeText(requireContext(),"Please, Add Item First",Toast.LENGTH_SHORT).show()
          //  findNavController().popBackStack()
            findNavController().navigate(MyCartFargmentDirections.actionCartToEmptyCartFragment())

        }else {

            ////Cart Logic
            subscribeToLiveData(cartAdapter)
            suspendWindowViewModel.progressBar(true)
            ///Getting all products in cart
            getAllCartViewModel.getAllCarts()
            //Update all products in cart

            binding.checkOutBtn.setOnClickListener {
                ///Transfer Cart to order
                createCartViewModel.createOrder()

                ///Data Observation to Api
                createCartViewModel.createOrderLiveData.observe(viewLifecycleOwner) {
                    if (it?.success.toString().equals("order is done")) { Toast.makeText(requireContext(), "Order Add Successfully :)", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Sorry, Failed to Create Order! :(", Toast.LENGTH_SHORT).show()
                    }
                }
            }

//            binding.checkOutBtn.setOnClickListener {
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.component =
//                    ComponentName("com.easy_pro_code.wallet", "com.easy_pro_code.wallet.payment.PaymentActivity")
//                startActivity(intent)
//            }

        }

        binding.applyBtn.setOnClickListener {
            if (binding.enterPromoCode.getText().toString().equals("panda20")){
                binding.promoCodeActiveted.visibility = View.VISIBLE
                totalAfterDiscount = (total*0.8).toInt()
                binding.totalPrice.setText("YER "+totalAfterDiscount.toString()+".00")
            }
            else{
                Toast.makeText(requireContext(), "Invalid Code", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addACreditCart.setOnClickListener {
            Toast.makeText(requireContext(), "Soon :)", Toast.LENGTH_SHORT).show()
        }

    return binding.root
    }

    private fun setupAdapterClickListener(cartAdapter: CartRecyclerView) {
        cartAdapter.itemCounterChangeListener=object :CartRecyclerView.ItemCounterChangeListener{
            override fun onCounterButtonsClickListener(
                newCount: Int,
                newPrice: Int,
                priceDisplay: TextView,
                numberOfProduct: TextView,
                cart: MyCartModel
            ) {
                currentProductPrice=cart.price
                currentCount=newCount
                currentPriceDisplay=priceDisplay
                currentPrice=newPrice
                suspendWindowViewModel.progressBar(true)
                getAllCartViewModel.updateCart(newCount,cart.productId!!)
                currentNumberOfProduct=numberOfProduct
                currentCart=cart
            }

        }
    }


    ////Cart Logic Impl
    private fun subscribeToLiveData(cartAdapter:CartRecyclerView){
        getAllCartViewModel.getcartsLiveData.observe(viewLifecycleOwner){

            if (it.carts!!.isEmpty()){
                suspendWindowViewModel.progressBar(false)
                findNavController().navigate(MyCartFargmentDirections.actionCartToEmptyCartFragment())
            }
            else{
                total=0
                shipping=0
                //            Log.e("Ziad Adapter live data",it.toString())
                it.carts.let {
                        cartsListResponse->
                    cartsListResponse?.map {
                            cartsItem ->
                        list = cartsItem?.items?.map {
                                productResponse->
                            val myCartModel = MyCartModel(
                                price = productResponse?.productId?.price?.toInt(),
                                title = productResponse?.productId?.title.toString(),
                                userId = cartsItem.userId.toString(),
                                image = productResponse?.productId?.image,
                                count = productResponse?.number,
                                productId = productResponse?.productId?.id.toString(),
                                cartId = cartsItem.id.toString(),
                                date = cartsItem.date.toString(),
                            )
                           total+=productResponse?.productId?.price?.toInt()!! * productResponse?.number!!
                            myCartModel
                        }
                       binding.subtotalPrice.setText("YER "+total.toString()+".00")
                    }

                    cartAdapter.submitList(list)
                    //nav_button
                    navBar = requireActivity().findViewById(R.id.bottom_nav)
                    navBar.getOrCreateBadge(R.id.cart).number = list?.size!!

                    Log.e("Ziad Adapter data",list?.size.toString())
                    Log.e("userId" , AuthUtils.manager.fetchData().id.toString())
//                   binding.subtotalPrice.setText("YER "+total.toString()+".00")
                    if (total<1000)  {
                        shipping=50* list?.size!!
                        binding.shippingFeePrice.setText("YER "+shipping.toString()+".00")
                    }
                    else {
                        binding.shippingFeePrice.setText("free")
                    }
                        binding.totalPrice.setText("YER " + (total + shipping).toString() + ".00")
                }
                suspendWindowViewModel.progressBar(false)
            }
        }

        getAllCartViewModel.updateCartLiveDate.observe(viewLifecycleOwner){
            it?.let {

                currentNumberOfProduct?.setText((currentNumberOfProduct?.text.toString().toInt()+currentCount!!).toString())
                total+=currentCount!! * currentProductPrice!!
                Log.i("cureeeeeeeent price isssss",currentPrice.toString())
                currentPriceDisplay?.text = "YER ${currentPrice}.00"
                binding.subtotalPrice.setText("YER "+total.toString()+".00")
                if (total<1000){
                    shipping=it.update?.items?.size!! *50
                    binding.shippingFeePrice.setText("YER "+shipping.toString()+".00")
                }else{
                    shipping=0
                    binding.shippingFeePrice.setText("free")
                }
                binding.totalPrice.setText("YER " + (total + shipping).toString() + ".00")
                suspendWindowViewModel.progressBar(false)
                currentCart?.count = currentCart?.count?.plus(currentCount!!)
                getAllCartViewModel.updateCartLiveDate.value=null
            }
        }
    }

    ///////Auto Complete Fragment Components
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

    override fun onStop() {
        super.onStop()
         findNavController().popBackStack()
    }

}







