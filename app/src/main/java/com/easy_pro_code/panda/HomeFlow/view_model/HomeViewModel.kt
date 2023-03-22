package com.easy_pro_code.panda.HomeFlow.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.HomeFlow.models.fromProductItemToWishProduct
import com.easy_pro_code.panda.data.Models.local_database.WishProduct
import com.easy_pro_code.panda.data.Models.remote_backend.CategoryResponse
import com.easy_pro_code.panda.data.Models.remote_backend.GetAllProductsResponse
import com.easy_pro_code.panda.data.Models.remote_backend.OffersResponse
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import com.easy_pro_code.panda.data.local_database.app_database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {

    val productsLiveData:MutableLiveData<GetAllProductsResponse> = MutableLiveData<GetAllProductsResponse>()
    val offersLiveData:MutableLiveData<OffersResponse> =MutableLiveData()
    val categoryLiveData:MutableLiveData<CategoryResponse> =MutableLiveData()
    private val wishListDao= AppDatabase.getInstance().wishListDao()

    private val offersWebService=ApiManager.getOfferApi()
    private val productWebService=ApiManager.getProductApi()
    private val categoryWebService=ApiManager.getCategoryApi()


    fun addToWishList(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("here is produt added",product.toString())
            wishListDao.addWishProduct(product.fromProductItemToWishProduct())
        }
    }

    fun removeFromWishList(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            wishListDao.removeWishProduct(product.fromProductItemToWishProduct())
        }
    }
    fun getAllProducts(){
        viewModelScope.launch {
            productsLiveData.value=productWebService.getAllProducts()
        }
    }

    fun getAllOffers(){
        viewModelScope.launch {
            offersLiveData.value=offersWebService.getOffers()
        }
    }

    fun getAllCategories(){
        viewModelScope.launch {
            categoryLiveData.value=categoryWebService.getAllCategories()
        }
    }


}