package com.easy_pro_code.panda.AuthFlow.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy_pro_code.panda.data.Models.remote_backend.SignUp
import com.easy_pro_code.panda.data.Models.remote_backend.SignUpRequest
import com.easy_pro_code.panda.data.Models.remote_backend.UserData

import com.easy_pro_code.panda.data.api.api_manager.ApiManager
import com.easy_pro_code.panda.data.api.web_services.AuthenticationWebService
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import retrofit2.HttpException
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {
    private val _userLiveData = MutableLiveData<SignUp>()
    val userLiveData: LiveData<SignUp>
        get() = _userLiveData

    private val authenticationWebService: AuthenticationWebService = ApiManager.getAuthenticationApi()

    fun signUp(userName:String,firstName:String,lastName:String,phoneNumber:String,email: String){

        viewModelScope.launch {
            try {
                val userRequest= SignUpRequest(
                    phone = phoneNumber,
                    email = email,
                    userName = userName
                )
                _userLiveData.value=authenticationWebService.signUp(userRequest)
            }catch (t:Throwable){
                when(t){
                    is HttpException ->{
                        t?.response()?.errorBody()
                        val response= SignUp(message = "Connection failed,please try again")
                        _userLiveData.value=response
                    }
                }

            }catch (ex:Exception){

            }

        }
    }

    fun onSucessfulsignIn(user: UserData, phoneNumber: String)
    {
        AuthUtils.manager.saveAuthToken(user,phoneNumber)
    }
}