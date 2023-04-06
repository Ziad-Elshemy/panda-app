package com.easy_pro_code.panda.HomeFlow.view_model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.HomeFlow.models.MyCartModel
import com.easy_pro_code.panda.data.Models.remote_backend.GetCartById
import com.easy_pro_code.panda.data.Models.remote_backend.UpdateCartRequest
import com.easy_pro_code.panda.data.Models.remote_backend.UpdateCartResponse
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetCartViewModel : ViewModel() {

    val getcartsLiveData: MutableLiveData<GetCartById> = MutableLiveData<GetCartById>()
    val updateCartLiveDate:MutableLiveData<UpdateCartResponse> = MutableLiveData<UpdateCartResponse>()


    private val getCartWebService = ApiManager.getAllCartApi()

    fun getAllCarts(){
        viewModelScope.launch {
            try {
                getcartsLiveData.value = getCartWebService.getCarts()
                Log.e("Ziad Cart VM","hi")
            }   catch (ex:java.lang.Exception){
                Log.i("ex from get Carts ",ex.message!!)
            }
        }
    }

    fun updateCart (number: Int , productId: String){
        viewModelScope.launch {
            try {
                updateCartLiveDate.value =getCartWebService.updateCart(updateCartRequest = UpdateCartRequest(productId,number))
            }catch (ex:Exception){
                Log.i("error in product by category","error")
            }
        }

    }

}