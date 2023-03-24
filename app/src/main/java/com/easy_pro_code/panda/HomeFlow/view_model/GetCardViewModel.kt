package com.easy_pro_code.panda.HomeFlow.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.HomeFlow.models.MyCartModel
import com.easy_pro_code.panda.data.Models.remote_backend.GetCartById
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetCartViewModel : ViewModel() {

    val getcartsLiveData: MutableLiveData<GetCartById> = MutableLiveData<GetCartById>()

    private val getCartWebService = ApiManager.getAllCartApi()

    fun getAllCarts(){
        viewModelScope.launch {
            try {
                val test=async { getCartWebService.getCarts() }
                getcartsLiveData.value = test.await()
                Log.e("Ziad Cart VM",test.await().toString())
            }   catch (ex:java.lang.Exception){
                Log.i("ex from get Carts ",ex.message!!)
            }
        }
    }

}