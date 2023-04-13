package com.easy_pro_code.panda.HomeFlow.presentations.productPage

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.models.SlideModel
import com.easy_pro_code.panda.HomeFlow.models.Electronics
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.HomeFlow.models.fromElectronicsToProduct
import com.easy_pro_code.panda.HomeFlow.presentations.home.*
import com.easy_pro_code.panda.HomeFlow.view_model.AddCartViewModel
import com.easy_pro_code.panda.HomeFlow.view_model.CategoryViewModel
import com.easy_pro_code.panda.HomeFlow.view_model.SuspendWindowViewModel
import com.easy_pro_code.panda.MainActivity
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.remote_backend.OrderCart
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.databinding.FragmentProductPageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class ProductPageFragment:Fragment() {
    lateinit var viewBinding:FragmentProductPageBinding
    private lateinit var  addCartViewModel: AddCartViewModel
    private lateinit var categoryViewModel:CategoryViewModel
    private val suspendWindowViewModel: SuspendWindowViewModel by activityViewModels()
    lateinit var cart: OrderCart
    val sessionManager = AuthUtils.manager
    var pos:Int = 0


    lateinit var selectedProduct:Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addCartViewModel= ViewModelProvider(this).get(AddCartViewModel::class.java)
        categoryViewModel=ViewModelProvider(this).get(CategoryViewModel::class.java)
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
        val phone = args.phone
        val electronic = args.electronics

        //Spinner Value
        val number = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val spinner = viewBinding.dropdownMenuNumOfItems
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, number)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                //Toast.makeText(requireContext(), getString(R.string.selected_item) + " " + number[position], Toast.LENGTH_SHORT).show()
                view?.let {
                    pos = number[position].toInt()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(requireContext(), "Please select number of product", Toast.LENGTH_SHORT).show()
            }
        }

        val categoryAdapter=ElectronicsRecyclerView(listOf(), listOf())
        viewBinding.similarProductsRv.adapter=categoryAdapter

        suspendWindowViewModel.progressBar(true)
        if (product != null) {
            selectedProduct = product
            viewBinding.productTitleTv.setText(product.title)
            viewBinding.totalPriceET.setText(product.price)
            viewBinding.reviewsSubTitleText.setText(product.title)
            viewBinding.categoryTitleTv.setText(product.category)
            viewBinding.rateText1.setText((product.rate).toString())
            viewBinding.newTotalPriceET.isVisible = false
            viewBinding.pricesLineSeparator1.isVisible = false
            viewBinding.newPriceCurrencyText.isVisible = false
            viewBinding.rateIcon1.setText((product.rate).toString())

            categoryViewModel.getProductByCategory(product.category!!)
        } else if (offer != null) {
            selectedProduct = offer.product
            viewBinding.productTitleTv.setText(offer.product.title)
            viewBinding.totalPriceET.setText(offer.product.price)
            viewBinding.newTotalPriceET.setText(offer.newPrice)
            viewBinding.rateIcon1.setText((offer.product.rate.toString()))
            viewBinding.reviewsSubTitleText.setText(offer.product.title)
            viewBinding.categoryTitleTv.setText(offer.product.category)
            viewBinding.rateText1.setText((offer.product.rate).toString())
            viewBinding.totalPriceET.paintFlags = viewBinding.totalPriceET.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG

            categoryViewModel.getProductByCategory(offer.product.category!!)
        } else if (phone != null) {
            selectedProduct = phone.product
            viewBinding.productTitleTv.setText(phone.product.title)
            viewBinding.totalPriceET.setText(phone.product.price)
            viewBinding.newTotalPriceET.isVisible= false
            viewBinding.pricesLineSeparator1.isVisible = false
            viewBinding.newPriceCurrencyText.isVisible = false
            viewBinding.rateIcon1.setText((phone.product.rate.toString()))
            viewBinding.reviewsSubTitleText.setText(phone.product.title)
            viewBinding.categoryTitleTv.setText(phone.product.category)
            viewBinding.rateText1.setText((phone.product.rate).toString())
//            viewBinding.totalPriceET.paintFlags = viewBinding.totalPriceET.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            categoryViewModel.getPhoneProductByCategory(phone.product.category!!)
        }  else if (electronic != null) {
            selectedProduct = electronic.product
            viewBinding.productTitleTv.setText(electronic.product.title)
            viewBinding.totalPriceET.setText(electronic.product.price)
            viewBinding.newTotalPriceET.isVisible= false
            viewBinding.pricesLineSeparator1.isVisible = false
            viewBinding.newPriceCurrencyText.isVisible = false
            viewBinding.rateIcon1.setText((electronic.product.rate.toString()))
            viewBinding.reviewsSubTitleText.setText(electronic.product.title)
            viewBinding.categoryTitleTv.setText(electronic.product.category)
            viewBinding.rateText1.setText((electronic.product.rate).toString())
//            viewBinding.totalPriceET.paintFlags = viewBinding.totalPriceET.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG

        }
        viewBinding.variantColorContainer.isVisible=false
        viewBinding.variantSizeContainer.isVisible=false
        viewBinding.otherVariantsContainer.isVisible=false
        val map= hashMapOf<String,String>()
        selectedProduct.productVariant?.variant?.forEach{

            if(it?.key?.lowercase()=="color"){
                viewBinding.variantColorContainer.isVisible=true
                val variantColor= VariantColorRecyclerViewAdapter()
                viewBinding.rvVariantColor.adapter=variantColor
                variantColor.onVariantColorClickListener=object : VariantColorRecyclerViewAdapter.OnVariantColorClickListener{
                    override fun onClick(color: String) {
                        viewBinding.colorName.setText(color)
                    }
                }
                variantColor.submitList(it.value)
            }else if(it?.key?.lowercase()=="size"){
                viewBinding.variantSizeContainer.isVisible=true
                val variantSize= VariantSizeRecyclerViewAdapter()
                viewBinding.rvVariantSize.adapter=variantSize
                variantSize.onVariantSizeClickListener=object :VariantSizeRecyclerViewAdapter.OnVariantSizeClickListener{
                    override fun onClick(size: String) {
                        viewBinding.sizeName.setText(size)
                    }

                }
                variantSize.submitList(it.value)
            }else{
                it?.let {
                    map.put(key = it.key!!,it.value?.get(0)!!)
                }
            }
        }

        if (map.size!=0){
            viewBinding.otherVariantsContainer.isVisible=true
            val variantOther= VariantOtherRecyclerViewAdapter(map)
            viewBinding.rvOtherVariants.adapter=variantOther
        }


        //add cart button
        viewBinding.addToCartBtn.setOnClickListener {

            if (sessionManager.getCartId() == null) {
                if (AuthUtils.manager.fetchData().id == null) {
                    val LoginIntent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(LoginIntent)
                    requireActivity().finish()
                    Toast.makeText(requireContext(), "please login first", Toast.LENGTH_SHORT).show()
                } else {
                    Log.i(
                        "selectedProduct page:",
                        AuthUtils.manager.fetchData().id.toString() + ",  pID:" + selectedProduct?.id.toString() + ",  pos:" + pos.toString()
                    )
                    addCartViewModel.addToCart(AuthUtils.manager.fetchData().id.toString(), selectedProduct?.id.toString(), pos)
                    addCartViewModel.cartsLiveData.observe(viewLifecycleOwner) {
                        sessionManager.saveCartId(it.cart?.id.toString())
                    }
                    Toast.makeText(requireContext(), "Add To Cart", Toast.LENGTH_SHORT).show()
                    initDialog()
                }
            } else {
                addCartViewModel.updateCart(pos, selectedProduct.id.toString())
                Toast.makeText(requireContext(), "Update Cart", Toast.LENGTH_SHORT).show()
                initDialog()
            }
        }

        viewBinding.downloadIcon.setOnClickListener{

            runBlocking(Dispatchers.IO){

                val drawable = viewBinding.productImage.getDrawable() as BitmapDrawable
                val bitmap = drawable.bitmap

                val downloadFolder =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

                val mypath = File(downloadFolder?.absolutePath+"/panda")
                if (!mypath.exists()){
                    mypath.mkdir()
                }

                val fileName = String.format("%dMichael.jpg", System.currentTimeMillis())
                val outFile: File = File(mypath, fileName)
//                if (!outFile.exists()){
//                    outFile.createNewFile()
//                }
                val fos: FileOutputStream
                try {
                    fos = FileOutputStream(outFile)
                    // Use the compress method on the BitMap object to write image to the OutputStream
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.flush()
                    fos.close()
                    //  Toast.makeText(requireContext(), "Download", Toast.LENGTH_SHORT).show()

//                val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
//                intent.setData(Uri.fromFile(outFile))
                    MediaScannerConnection.scanFile(requireContext(), arrayOf(outFile.toString()), null, null)

                    Toast.makeText(requireContext(), "Download", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


        }

        val imageList = ArrayList<SlideModel>()

        imageList.add(
            SlideModel(
                "https://1.bp.blogspot.com/-1X6hcvRd_oM/XiWtkBAxBNI/AAAAAAAAEls/l2nPQ_--FDEI3bZijfe1S42qIV8D9HXpgCNcBGAsYHQ/s16000/%25D8%25A7%25D8%25B3%25D8%25B9%25D8%25A7%25D8%25B1-%25D8%25A7%25D9%2584%25D8%25A3%25D8%25AC%25D9%2587%25D8%25B2%25D9%2587-%25D8%25A7%25D9%2584%25D9%2583%25D9%2587%25D8%25B1%25D8%25A8%25D8%25A7%25D8%25A6%25D9%258A%25D9%2587-%25D9%2581%25D9%2589-%25D8%25A8%25D9%2589-%25D8%25AA%25D9%2583.jpg",
            )
        )
        imageList.add(
            SlideModel(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuqPR7EC4ClrZ03mogdx_RvMYP_9GTjk8VGN1L34b047_7FW_eNyX-x7NZmy7fg9kiNu8&usqp=CAU")
        )
        imageList.add(
            SlideModel(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSnAw00L6xkzvyxwzzZkozRXnoiDEVCAXwjD3szIcWuHwmasFbX5cnCabCzXKHhSoBQKr0&usqp=CAU",
            )

        )
        try {
            val base64String = selectedProduct.image
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            viewBinding.productImage.setImageBitmap(decodedByte)
        } catch (E: Exception) {
            Log.i("Mokhtar", selectedProduct.image.toString())

        }
        val imageSlider = viewBinding.productImageSlider
        imageSlider.setImageList(imageList)
        categoryAdapter.onElectronicClickListener=object :ElectronicsRecyclerView.OnElectronicClickListener{
            override fun onClick(electronic: Electronics) {
                findNavController().navigate(ProductPageFragmentDirections.actionProductPageFragmentSelf(electronics=electronic))
            }

            override fun onCheck(electronic: Electronics) {
                TODO("Not yet implemented")
            }

            override fun onUnCheck(electronic: Electronics) {
                TODO("Not yet implemented")
            }

        }
        subscribeToLiveData(categoryAdapter)
        return viewBinding.root
    }

    private fun subscribeToLiveData(categoryAdapter: ElectronicsRecyclerView) {
        categoryViewModel.productsLiveData.observe(viewLifecycleOwner){
            categoryAdapter.submitList(it.categoryProducts.fromElectronicsToProduct())
            suspendWindowViewModel.progressBar(false)
        }

        categoryViewModel.electronicsProductsLiveData.observe(viewLifecycleOwner){
            categoryAdapter.submitList(it.categoryProducts.fromElectronicsToProduct())
            suspendWindowViewModel.progressBar(false)
        }

        categoryViewModel.phonesProductsLiveData.observe(viewLifecycleOwner){
            categoryAdapter.submitList(it.categoryProducts.fromElectronicsToProduct())
            suspendWindowViewModel.progressBar(false)
        }
    }

    fun initDialog(){
        val view = layoutInflater.inflate(R.layout.cart_dialog,null)
        val cartBoxBuilder = AlertDialog.Builder(requireContext()).setView(view).create()
        cartBoxBuilder.show()
        val continueShoppingButton: Button = view.findViewById(R.id.continueToShopping)
        continueShoppingButton.setOnClickListener {
            findNavController().navigate(ProductPageFragmentDirections.actionProductPageFragmentToHomeFragment())
            cartBoxBuilder.dismiss()
        }
        val yesButton: Button = view.findViewById(R.id.yes)
        yesButton.setOnClickListener {
            findNavController().navigate(ProductPageFragmentDirections.actionProductPageFragmentToMyCartFargment())
            cartBoxBuilder.dismiss()
        }
    }
}