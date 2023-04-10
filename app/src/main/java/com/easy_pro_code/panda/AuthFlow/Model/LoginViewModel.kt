package com.easy_pro_code.panda.AuthFlow.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.SignInRequest
import com.easy_pro_code.panda.data.Models.remote_backend.UserData
import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import com.easy_pro_code.panda.data.api.web_services.AuthenticationWebService
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import kotlinx.coroutines.launch
import retrofit2.HttpException



class LoginViewModel : ViewModel(){

    private val _userLiveData = MutableLiveData<UserData?>()
    val userLiveData: LiveData<UserData?>
        get() = _userLiveData
    private val authenticationWebService: AuthenticationWebService = ApiManager.getAuthenticationApi()

    //sessionManager

    val sessionManager = AuthUtils.manager

    fun autoSignIn(): UserData {
        return  sessionManager.fetchData()
    }

    fun logIn(phoneNumber:String){
        viewModelScope.launch {

            try {
                val userRequest= SignInRequest(phoneNumber)
                _userLiveData.value=authenticationWebService.signIn(userRequest)
            }catch (t:Throwable){
                when(t){
                    is HttpException ->{
                        when (t.code())
                        {
                            400 -> {

                                Log.i("Ziad: error" , "400")
                            }
                            else -> {

                                Log.i("Ziad: error" , "Something went Wrong")

                            }
                        }

                    }
                }

            } catch (_: Exception) {

            }

        }
    }

    fun clearLiveData(){
        _userLiveData.value= null
    }



}

