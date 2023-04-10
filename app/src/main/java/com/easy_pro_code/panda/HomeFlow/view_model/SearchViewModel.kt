package com.easy_pro_code.panda.HomeFlow.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.SearchRequest
import com.easy_pro_code.panda.data.Models.remote_backend.SearchResponse
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import kotlinx.coroutines.launch

class SearchViewModel:ViewModel() {
    val liveData:MutableLiveData<SearchResponse> = MutableLiveData()
    private val webService=ApiManager.getProductApi()
    fun searchForProduct(name:String){
        viewModelScope.launch {
            try {
                liveData.value=webService.search(SearchRequest(name))
            }catch (ex:Exception){
                Log.i("error in product by category","error")
            }
        }
    }
}