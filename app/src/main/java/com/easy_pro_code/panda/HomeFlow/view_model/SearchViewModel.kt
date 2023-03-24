package com.easy_pro_code.panda.HomeFlow.view_model

import androidx.lifecycle.LiveData
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
            liveData.value=webService.search(SearchRequest(name))
        }
    }
}