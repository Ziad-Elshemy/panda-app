package com.easy_pro_code.panda.HomeFlow.presentations.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.models.SlideModel
import com.easy_pro_code.panda.HomeFlow.models.*
import com.easy_pro_code.panda.BuildConfig.MAPS_API_KEY
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.HomeFlow.view_model.*
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.local_database.WishProduct
import com.easy_pro_code.panda.databinding.FragmentHomeBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {

    private var data: Place? = null
    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private val suspendWindowViewModel:SuspendWindowViewModel by activityViewModels()
    private lateinit var wishListViewModel:WishListViewModel
    private val productsList:List<Product>? = listOf()
    private val offersList:List<Offer>? = listOf()
    private val phonesList:List<Phone>? = listOf()
    private val categoryList:List<String>?= listOf()
    private lateinit var edTextObj:EditText
    private var city:String=""
    private var edTextId:Int=-1
    private var wishList:List<WishProduct>? = listOf()

    private  val createAddressViewModel:CreateAddressViewModel by activityViewModels()





    var startAutocompleteIntentListener =
        View.OnClickListener { view: View ->
            edTextObj= view as EditText
            startAutocompleteIntent()
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel=ViewModelProvider(this).get(HomeViewModel::class.java)
        wishListViewModel=ViewModelProvider(this).get(WishListViewModel::class.java)
        categoryViewModel=ViewModelProvider(this).get(CategoryViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        val imageList = ArrayList<SlideModel>()

        imageList.add(
            SlideModel(
                "https://1.bp.blogspot.com/-1X6hcvRd_oM/XiWtkBAxBNI/AAAAAAAAEls/l2nPQ_--FDEI3bZijfe1S42qIV8D9HXpgCNcBGAsYHQ/s16000/%25D8%25A7%25D8%25B3%25D8%25B9%25D8%25A7%25D8%25B1-%25D8%25A7%25D9%2584%25D8%25A3%25D8%25AC%25D9%2587%25D8%25B2%25D9%2587-%25D8%25A7%25D9%2584%25D9%2583%25D9%2587%25D8%25B1%25D8%25A8%25D8%25A7%25D8%25A6%25D9%258A%25D9%2587-%25D9%2581%25D9%2589-%25D8%25A8%25D9%2589-%25D8%25AA%25D9%2583.jpg",
            )
        )
        imageList.add(
            SlideModel(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuqPR7EC4ClrZ03mogdx_RvMYP_9GTjk8VGN1L34b047_7FW_eNyX-x7NZmy7fg9kiNu8&usqp=CAU"            )

        )
        imageList.add(
            SlideModel(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSnAw00L6xkzvyxwzzZkozRXnoiDEVCAXwjD3szIcWuHwmasFbX5cnCabCzXKHhSoBQKr0&usqp=CAU",
            )

        )
        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)

        val productsAdapter=ProductsHomeRecyclerView(productsList,wishList,lifecycleScope)
        binding.productRv.adapter=productsAdapter
        productsAdapter.submitList(productsList)
        val offersAdapter=OffersRecyclerView(offersList)
        offersAdapter.submitList(offersList)
        binding.offersRv.adapter=offersAdapter
        val categoriesAdapter=CategoryRecyclerViewAdapter(categoryList)
        binding.categoriesRv.adapter=categoriesAdapter
        val phonesAdapter = PhonesRecyclerView(phonesList)
        binding.phonesRv.adapter=phonesAdapter
        setAdapterClickListener(productsAdapter,offersAdapter,categoriesAdapter,phonesAdapter)
        subscribeToLiveData(productsAdapter,offersAdapter,categoriesAdapter,phonesAdapter)
        homeViewModel.getAllProducts()

        homeViewModel.getAllOffers()
        homeViewModel.getAllCategories()

        categoryViewModel.getProductByCategory("Phones")
        suspendWindowViewModel.progressBar(true)

        //// initial plasces
        initPlacesSdk()
        ////calling AutoCompelete
        binding.locationDetection.setOnClickListener(startAutocompleteIntentListener)
        binding.searchIcon.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductSearchFragment())
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=false
        }
        return binding.root

    }

    private fun setAdapterClickListener(
        productsAdapter: ProductsHomeRecyclerView,
        offersAdapter: OffersRecyclerView,
        categoriesAdapter: CategoryRecyclerViewAdapter,
        phonesAdapter: PhonesRecyclerView
    ) {
        productsAdapter.onProductClickListener=object :ProductsHomeRecyclerView.OnProductClickListener{
            override fun onClick(product: Product) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductPageFragment(
                        product = product,
                        offer = null,
                        phone = null
                    )
                )
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=false

            }

            override fun onCheck(product: Product) {
                wishListViewModel.addToWishList(product)
            }

            override fun onUnCheck(product: Product) {
                wishListViewModel.removeFromWishList(product)
            }

        }

        offersAdapter.onOfferClickListener=object :OffersRecyclerView.OnOfferClickListener{
            override fun onClick(offer: Offer) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductPageFragment(
                    product = null,
                    offer = offer,
                    phone = null
                )
                )
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=false

            }

            override fun onCheck(offer: Offer) {
                wishListViewModel.addToWishList(offer.product)
            }

            override fun onUnCheck(offer: Offer) {
                wishListViewModel.removeFromWishList(offer.product)
            }

        }

        phonesAdapter.onPhoneClickListener=object :PhonesRecyclerView.OnPhoneClickListener{
            override fun onClick(phone: Phone) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductPageFragment(
                    product = null,
                    offer = null,
                    phone = phone
                )
                )
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=false

            }

            override fun onCheck(phone: Phone) {
                wishListViewModel.addToWishList(phone.product)
            }

            override fun onUnCheck(phone: Phone) {
                wishListViewModel.removeFromWishList(phone.product)
            }

        }

        categoriesAdapter.onCategoryClickListener=object :CategoryRecyclerViewAdapter.OnCategoryClickListener{
            override fun onClick(category: String) {
                Log.i("onCategoryClickListener",category)
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoriesFragment2(category))
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=false
            }
        }
    }


    private fun initPlacesSdk() {
        // Initialize the SDK
        Places.initialize(requireContext().applicationContext,MAPS_API_KEY)
        // Create a new PlacesClient instance
        Places.createClient(requireContext())
    }

    private fun subscribeToLiveData(
        productsAdapter: ProductsHomeRecyclerView,
        offersAdapter: OffersRecyclerView,
        categoriesAdapter: CategoryRecyclerViewAdapter,
        phonesAdapter: PhonesRecyclerView
    ) {
        homeViewModel.productsLiveData.observe(viewLifecycleOwner){
            suspendWindowViewModel.progressBar(false)
            productsAdapter.submitList(it?.products.fromProductToProduct())
        }

        homeViewModel.offersLiveData.observe(viewLifecycleOwner){
            offersAdapter.submitList(it.offers.fromOfferToProduct())
        }

        categoryViewModel.productsLiveData.observe(viewLifecycleOwner){
            phonesAdapter.submitList(it.categoryProducts.fromPhoneToProduct())
        }

        homeViewModel.categoryLiveData.observe(viewLifecycleOwner){
            categoriesAdapter.submitList(it.category.categoryItemToMainCategoryName())
        }
        wishListViewModel.wishListLiveData.observe(viewLifecycleOwner){
            wishList=it
            productsAdapter.submitWishList(wishList)
        }

        createAddressViewModel.createAddressWithCacheLiveData.observe(viewLifecycleOwner){
            createAddressViewModel.deliveryLocation=data?.name
            Log.i("with cache",data?.name.toString())
        }

        createAddressViewModel.createAddressWithoutCacheLiveData.observe(viewLifecycleOwner){
            createAddressViewModel.deliveryLocation=data?.name
            Log.i("without cache",data?.name.toString())
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



    private fun changeEditTextDataAndTripPoints(data: Place?) {
        edTextId=edTextObj.id
        edTextObj.setText(data?.name.toString())
    }



    private fun hideSuspendWindow(){
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
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

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=true
    }
}


