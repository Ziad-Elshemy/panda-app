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
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.models.SlideModel
import com.easy_pro_code.panda.HomeFlow.models.*
import com.easy_pro_code.panda.BuildConfig.MAPS_API_KEY
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.HomeFlow.view_model.HomeViewModel
import com.easy_pro_code.panda.HomeFlow.view_model.SuspendWindowViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentHomeBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private val suspendWindowViewModel:SuspendWindowViewModel by activityViewModels()
    private val productsList:List<Product>? = listOf()
    private val offersList:List<Offer>? = listOf()
    private val categoryList:List<String>?= listOf()
    private lateinit var edTextObj:EditText
    private var city:String=""
    private var edTextId:Int=-1





    var startAutocompleteIntentListener =
        View.OnClickListener { view: View ->
            edTextObj= view as EditText
            startAutocompleteIntent()
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel=ViewModelProvider(this).get(HomeViewModel::class.java)
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

        val productsAdapter=ProductsHomeRecyclerView(productsList)
        binding.productRv.adapter=productsAdapter
        productsAdapter.submitList(productsList)
        val offersAdapter=OffersRecyclerView(offersList)
        offersAdapter.submitList(offersList)
        binding.offersRv.adapter=offersAdapter
        val categoriesAdapter=CategoryRecyclerViewAdapter(categoryList)
        binding.categoriesRv.adapter=categoriesAdapter
        setAdapterClickListener(productsAdapter,offersAdapter,categoriesAdapter)
        subscribeToLiveData(productsAdapter,offersAdapter,categoriesAdapter)
        homeViewModel.getAllProducts()

        homeViewModel.getAllOffers()
        homeViewModel.getAllCategories()
        suspendWindowViewModel.progressBar(true)

        //// initial plasces
        initPlacesSdk()
        ////calling AutoCompelete
        binding.locationDetection.setOnClickListener(startAutocompleteIntentListener)

        return binding.root

    }

    private fun setAdapterClickListener(
        productsAdapter: ProductsHomeRecyclerView,
        offersAdapter: OffersRecyclerView,
        categoriesAdapter: CategoryRecyclerViewAdapter
    ) {
        productsAdapter.onProductClickListener=object :ProductsHomeRecyclerView.OnProductClickListener{
            override fun onClick(product: Product) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductPageFragment(
                        product = product,
                        offer = null
                    )
                )
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=false

            }

            override fun onCheck(product: Product) {
                homeViewModel.addToWishList(product)
            }

            override fun onUnCheck(product: Product) {
                homeViewModel.removeFromWishList(product)
            }

        }

        offersAdapter.onOfferClickListener=object :OffersRecyclerView.OnOfferClickListener{
            override fun onClick(offer: Offer) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductPageFragment(
                    product = null,
                    offer = offer
                )
                )
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=false

            }

            override fun onCheck(offer: Offer) {
                homeViewModel.addToWishList(offer.product)
            }

            override fun onUnCheck(offer: Offer) {
                homeViewModel.removeFromWishList(offer.product)
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
        categoriesAdapter: CategoryRecyclerViewAdapter
    ) {
        homeViewModel.productsLiveData.observe(viewLifecycleOwner){
            suspendWindowViewModel.progressBar(false)
            productsAdapter.submitList(it.products.fromProductToProduct())
        }

        homeViewModel.offersLiveData.observe(viewLifecycleOwner){
            offersAdapter.submitList(it.offers.fromOfferToProduct())
        }

        homeViewModel.categoryLiveData.observe(viewLifecycleOwner){
            categoriesAdapter.submitList(it.category.categoryItemToMainCategoryName())
        }
    }



    private fun startAutocompleteIntent(){
        val fields = listOf(
            Place.Field.ADDRESS_COMPONENTS,
            Place.Field.LAT_LNG, Place.Field.VIEWPORT,
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
            val data= result?.data?.let { Autocomplete.getPlaceFromIntent(it) }
            Log.i("PLACE: ", data?.address.toString())
            val address=data?.address.toString().replace(" ","").split(",")
            val country= address[address.size-1]
            Log.i("city: ", city)
            if (this.city==city && country=="Egypt"){
                changeEditTextDataAndTripPoints(data)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=true
    }
    }


