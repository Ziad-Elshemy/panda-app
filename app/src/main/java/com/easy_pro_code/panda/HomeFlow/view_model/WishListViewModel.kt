package com.easy_pro_code.panda.HomeFlow.view_model

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.easy_pro_code.panda.data.local_database.app_database.AppDatabase

class WishListViewModel:ViewModel() {
    private val wishListDao=AppDatabase.getInstance().wishListDao()

    val wishListLiveData=Transformations.map(wishListDao.getAll()){
        it
    }
}