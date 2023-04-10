package com.easy_pro_code.panda.data.api.repos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.CategoryResponse
import com.easy_pro_code.panda.data.Models.remote_backend.GetAllProductsResponse
import com.easy_pro_code.panda.data.Models.remote_backend.OffersResponse
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import com.easy_pro_code.panda.data.api.web_services.CategoryWebService
import com.easy_pro_code.panda.data.api.web_services.OffersWebService
import com.easy_pro_code.panda.data.api.web_services.ProductWebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeRepository private constructor(
    private val offersWebService: OffersWebService = ApiManager.getOfferApi(),
    private val productWebService: ProductWebService = ApiManager.getProductApi(),
    private val categoryWebService: CategoryWebService = ApiManager.getCategoryApi()
) {



    companion object{

        private var homeRepository:HomeRepository?=null

        fun getInstance():HomeRepository{
            if (homeRepository==null){
                homeRepository= HomeRepository()
            }

            return homeRepository!!
        }

        val productsLiveData: MutableLiveData<GetAllProductsResponse?> = MutableLiveData<GetAllProductsResponse?>(null)
        val offersLiveData: MutableLiveData<OffersResponse> = MutableLiveData(null)
        val categoryLiveData: MutableLiveData<CategoryResponse> = MutableLiveData(null)


    }

    suspend fun getAllProducts(){
        try {
            productsLiveData.value= productWebService.getAllProducts()
        }catch (ex:Exception){
            Log.i("error in get all product","error")
        }
    }

    suspend fun getAllOffers(){
        try {
            offersLiveData.value= offersWebService.getOffers()
        }catch (ex:Exception){
            Log.i("error in get offers","error")
        }
    }

    suspend fun getAllCategories(){
        try {
            categoryLiveData.value= categoryWebService.getAllCategories()
        }catch (ex:Exception){
            Log.i("error in get all categories","error")
        }
    }


}