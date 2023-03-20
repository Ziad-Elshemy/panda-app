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
    fun onSucessfulsignIn(user: UserData,phoneNumber: String)
    {
        sessionManager.saveAuthToken(user,phoneNumber)
    }


    fun logIn(phoneNumber:String){
        viewModelScope.launch {

            try {
                val userRequest= SignInRequest(phoneNumber)
//                Log.i("Ziad: error" , "try loginViewModel ${userRequest.toString()}")
                _userLiveData.value=authenticationWebService.signIn(userRequest)
//                Log.i("Ziad: error" , _userLiveData.value.toString())
            }catch (t:Throwable){
                when(t){
                    is HttpException ->{
                        when (t.code())
                        {
                            400 -> {
//                                val response=UserData(message = "User Not found.")
//                                _userLiveData.value=response
                                Log.i("Ziad: error" , "400")
                            }
                            else -> {
//                                val response = UserData(message = "something went wrong")
//                                _userLiveData.value = response

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

