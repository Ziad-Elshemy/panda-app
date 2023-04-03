package com.easy_pro_code.panda.HomeFlow.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.*
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import com.easy_pro_code.panda.data.api.api_manager.CacheApiManager
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CreateAddressViewModel:ViewModel() {
    val createAddressWithCacheLiveData:MutableLiveData<CreateAddressWithCacheResponse> =MutableLiveData<CreateAddressWithCacheResponse>()
    val createAddressWithoutCacheLiveData:MutableLiveData<CreateAddresswithoutCacheResponse> =MutableLiveData<CreateAddresswithoutCacheResponse>()

    private val placeWebService=CacheApiManager.getPlaceSearch()
    private val createAddressWebService=ApiManager.getCreateAddressApi()

    var deliveryLocation:String?="Detect your Location"

    fun checkAddress(lat:String,lng:String,address:String){
        viewModelScope.launch {
            try {
                val placeSearchResponse=async { placeWebService.placeSearch(
                        CachePlaceSearchRequest(
                            Location(
                                jsonMemberLong = lng,
                                lat=lat
                            )
                        )
                ) }
                if (placeSearchResponse.await().orders==null){
                    createAddressWithoutCacheLiveData.value=createAddressWebService.createAddressWithoutCache(
                        CreateAddressWithoutCacheRequest(
                            userId = AuthUtils.manager.fetchData().id,
                            location = Location(
                                jsonMemberLong = lng,
                                lat=lat
                            ),
                            name = address
                        )
                    )
                }else{
                    createAddressWithCacheLiveData.value=createAddressWebService.createAddressWithCache(
                        CreateAddressWithCacheRequest(
                            userId = AuthUtils.manager.fetchData().id,
                            addressID = placeSearchResponse.await().orders?.id
                        )
                    )
                }
            }catch (ex:Exception){
                Log.i("error in product by category","error")
            }
        }
    }

}