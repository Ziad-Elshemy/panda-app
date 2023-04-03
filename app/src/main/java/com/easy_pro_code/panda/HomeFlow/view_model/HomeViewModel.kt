package com.easy_pro_code.panda.HomeFlow.view_model

import android.content.Intent
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.HomeFlow.models.fromProductItemToWishProduct
import com.easy_pro_code.panda.data.Models.local_database.WishProduct
import com.easy_pro_code.panda.data.Models.remote_backend.CategoryResponse
import com.easy_pro_code.panda.data.Models.remote_backend.GetAllProductsResponse
import com.easy_pro_code.panda.data.Models.remote_backend.OffersResponse
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import com.easy_pro_code.panda.data.api.repos.HomeRepository
import com.easy_pro_code.panda.data.local_database.app_database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Handler

class HomeViewModel:ViewModel() {
    private val repo:HomeRepository=HomeRepository.getInstance()
    var dataLoadedFlag=false
    val productsLiveData:LiveData<GetAllProductsResponse?> = Transformations.map(HomeRepository.productsLiveData){
        it
    }
    val offersLiveData:LiveData<OffersResponse> =Transformations.map(HomeRepository.offersLiveData){
        it
    }
    val categoryLiveData:LiveData<CategoryResponse> =Transformations.map(HomeRepository.categoryLiveData){
        it
    }


    init {
            getAllProducts()
            getAllOffers()
            getAllCategories()
    }


    fun getAllProducts(){
        viewModelScope.launch {
            repo.getAllProducts()
        }
    }

    fun getAllOffers(){
        viewModelScope.launch {
            repo.getAllOffers()
        }
    }

    fun getAllCategories(){
        viewModelScope.launch {
            repo.getAllCategories()
        }
    }



//    val productsLiveData:MutableLiveData<GetAllProductsResponse?> = MutableLiveData<GetAllProductsResponse?>()
//    val offersLiveData:MutableLiveData<OffersResponse> =MutableLiveData()
//    val categoryLiveData:MutableLiveData<CategoryResponse> =MutableLiveData()
//    private val offersWebService=ApiManager.getOfferApi()
//    private val productWebService=ApiManager.getProductApi()
//    private val categoryWebService=ApiManager.getCategoryApi()
//
//
//
//    fun getAllProducts(){
//        viewModelScope.launch {
//            productsLiveData.value= withContext(Dispatchers.IO){
//                productWebService.getAllProducts()
//            }
//
//        }
//    }
//
//    fun getAllOffers(){
//        viewModelScope.launch {
//            offersLiveData.value= withContext(Dispatchers.IO){offersWebService.getOffers()}!!
//        }
//    }
//
//    fun getAllCategories(){
//        viewModelScope.launch {
//            categoryLiveData.value= withContext(Dispatchers.IO){categoryWebService.getAllCategories()}!!
//        }
//    }


}