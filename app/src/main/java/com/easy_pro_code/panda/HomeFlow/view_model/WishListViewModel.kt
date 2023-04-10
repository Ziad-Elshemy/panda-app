package com.easy_pro_code.panda.HomeFlow.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.HomeFlow.models.fromProductItemToWishProduct
import com.easy_pro_code.panda.data.Models.local_database.WishProduct
import com.easy_pro_code.panda.data.local_database.app_database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishListViewModel:ViewModel() {
    private val wishListDao=AppDatabase.getInstance().wishListDao()

    var wishListLiveData:LiveData<List<WishProduct>?> =Transformations.map(wishListDao.getAll()){
        it
    }

    fun addToWishList(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.i("here is produt added",product.toString())
                wishListDao.addWishProduct(product.fromProductItemToWishProduct())
            }catch (ex:Exception){
                 Log.i("error in product by category","error")
            }
        }
    }

    fun removeFromWishList(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                wishListDao.removeWishProduct(product.fromProductItemToWishProduct())
            }catch (ex:Exception){
                Log.i("error in product by category","error")
            }
        }
    }
}