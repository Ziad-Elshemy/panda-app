package com.easy_pro_code.panda.HomeFlow.presentations.productPage

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.easy_pro_code.panda.R
import androidx.lifecycle.ViewModelProvider
import com.easy_pro_code.panda.HomeFlow.view_model.AddCartViewModel
import com.easy_pro_code.panda.databinding.FragmentProductPageBinding
import com.easy_pro_code.panda.data.Models.remote_backend.Cart
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.data.Models.remote_backend.OrderCart
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.HomeFlow.models.Product

class ProductPageFragment : Fragment() {
    lateinit var viewBinding: FragmentProductPageBinding
    private lateinit var addCartViewModel: AddCartViewModel
    lateinit var cart: OrderCart
    val sessionManager = AuthUtils.manager
    var pos: Int = 0

    lateinit var selectedProduct: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addCartViewModel = ViewModelProvider(this).get(AddCartViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_product_page,
            container,
            false
        )
        val args: ProductPageFragmentArgs by navArgs()
        val product = args.product
        val offer = args.offer


        //Spinner Value
        val number = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val spinner = viewBinding.dropdownMenuNumOfItems
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, number)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.selected_item) + " " + number[position],
                    Toast.LENGTH_SHORT
                ).show()
                pos = number[position].toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    requireContext(),
                    "Please select number of product",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        if (product != null) {
            selectedProduct = product
            viewBinding.totalPriceET.setText(product.price)
            viewBinding.reviewsSubTitleText.setText(product.title)
            viewBinding.categoryTitleTv.setText(product.category)
            viewBinding.rateText1.setText(product.rate.toString())
            viewBinding.newTotalPriceET.isVisible = false
            viewBinding.pricesLineSeparator1.isVisible = false
            viewBinding.newPriceCurrencyText.isVisible = false
            viewBinding.rateIcon1.setText(offer?.product?.rate.toString())

        } else if (offer != null) {
            selectedProduct = offer.product
            viewBinding.totalPriceET.setText(offer.product.price)
            viewBinding.newTotalPriceET.setText(offer.newPrice)
            viewBinding.rateIcon1.setText(offer.product.rate.toString())
            viewBinding.reviewsSubTitleText.setText(offer.product.title)
            viewBinding.categoryTitleTv.setText(offer.product.category)
            viewBinding.rateText1.setText(offer.product.rate.toString())
        }

        try {
            val base64String = selectedProduct.image
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            viewBinding.productImage.setImageBitmap(decodedByte)
        } catch (E: Exception) {
            Log.i("Mokhtar", selectedProduct.image.toString())

        }


        //add cart button
        viewBinding.addToCartBtn.setOnClickListener {

            if (sessionManager.getCartId() == null) {
                Log.i("Michael", pos.toString())
                if (AuthUtils.manager.fetchData().id == null) {
                    findNavController().popBackStack()
                    Toast.makeText(requireContext(), "please login first", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Log.i(
                        "selectedProduct page:",
                        AuthUtils.manager.fetchData().id.toString() + ",  pID:" + selectedProduct?.id.toString() + ",  pos:" + pos.toString()
                    )
                    addCartViewModel.addToCart(
                        AuthUtils.manager.fetchData().id.toString(),
                        selectedProduct?.id.toString(),
                        pos
                    )
                    addCartViewModel.cartsLiveData.observe(viewLifecycleOwner) {
                        sessionManager.saveCartId(it.cart?.id.toString())
                        Log.i("Michael", it.cart?.id.toString())
                    }
                    Toast.makeText(requireContext(), "Add To Cart", Toast.LENGTH_SHORT).show()
                }
            } else {
                addCartViewModel.updateCart(pos, selectedProduct?.id.toString())
                Toast.makeText(requireContext(), "Update Cart", Toast.LENGTH_SHORT).show()

            }
        }
        return viewBinding.root
    }
}