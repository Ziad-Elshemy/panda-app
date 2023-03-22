package com.easy_pro_code.panda.HomeFlow.presentations.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.models.SlideModel
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.HomeFlow.models.toProduct
import com.easy_pro_code.panda.HomeFlow.view_model.AddCartViewModel
import com.easy_pro_code.panda.HomeFlow.view_model.HomeViewModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private val productsList:List<Product>? = listOf()
    private val offersList:List<Product>? = listOf()
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


        val offersAdapter=ProductsHomeRecyclerView(offersList)
        offersAdapter.submitList(offersList)
        binding.offersRv.adapter=offersAdapter

        subscribeToLiveData(productsAdapter,offersAdapter)
        homeViewModel.getAllProducts()




        return binding.root
    }

    private fun subscribeToLiveData(
        productsAdapter: ProductsHomeRecyclerView,
        offersAdapter: ProductsHomeRecyclerView
    ) {
        homeViewModel.productsLiveData.observe(viewLifecycleOwner){
            productsAdapter.submitList(it.products.toProduct())
            offersAdapter.submitList(it.products.toProduct())
        }
    }

}