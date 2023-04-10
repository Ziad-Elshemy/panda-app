package com.easy_pro_code.panda.data.Models.remote_backend

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.easy_pro_code.panda.R
import java.time.LocalDateTime
import java.time.ZoneId

class SessionManager (context: Context) {

    private var prefsobj: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)!!
    private var prefsObjForAppState: SharedPreferences = context.getSharedPreferences("app_Stack_shared_pref", Context.MODE_PRIVATE)!!

    companion object {
        const val USER_TOKEN = "user_token"
        const val CART_ID = "cart_id"
        const val USER_NAME = "user_name"
        const val ROLE = "role"
        const val PHONE = "phone"
        const val ID = "id"
        const val EMAIL = "email"
        const val EXPIRE_DATE="expire"


        private var sessionManager: SessionManager? = null
        fun getInstance(context: Context): SessionManager {
            if (sessionManager == null) {
                sessionManager = SessionManager(context)
            }
            return sessionManager as SessionManager
        }
    }

    fun saveCartId(cartId:String){
        val editor = prefsobj.edit()
        editor.putString(CART_ID,cartId)
        editor.apply()
    }

    fun saveAuthToken(user: UserData, phone:String) {
        val editor = prefsobj.edit()
      //  editor.putString(USER_TOKEN, user.accessToken)
      //  editor.putString(USER_NAME,user.username)
       // editor.putStringSet(ROLE, user.roles?.toSet())
        editor.putString(EMAIL, user.email)
        editor.putString(PHONE, phone)
        editor.putString(USER_NAME,user.userName)
        Log.i("USER_TOKEN",user.token.toString())
        editor.putString(USER_TOKEN , user.token)
        user.id?.let { editor.putString(ID, it) }
        editor.putString(
            EXPIRE_DATE,
            LocalDateTime.now(ZoneId.of("UTC")).plusDays(1L).toString()
        )
        editor.apply()
    }
    /**
     * Function to fetch auth token
     */
    fun getPhone():String?{
        return prefsobj.getString(PHONE,null)
    }


    fun deleteData(){
        prefsobj.edit().clear().apply()
    }

    fun getToken():String?{
        return prefsobj.getString(USER_TOKEN,null)
    }

    fun getCartId():String?{
        return prefsobj.getString(CART_ID,null)
    }

    fun deleteCartID(){
        prefsobj.edit().putString(CART_ID,null).apply()
    }

    fun isTokenExpired():Boolean?{
        val current = LocalDateTime.now(ZoneId.of("UTC"))
        prefsobj.getString(EXPIRE_DATE,null)?.let {
            val expiration = LocalDateTime.parse(it)
            Log.v("current",current.toString())
            Log.v("exp", expiration.toString())
            return current.isEqual(expiration) || current.isAfter(expiration)
        }
        return null
    }
    fun clearAppState() {
        prefsObjForAppState.edit().clear().apply()
    }
    fun fetchData(): UserData {
        return UserData(
            token = prefsobj.getString(USER_TOKEN, null),
            email =prefsobj.getString(EMAIL, null),
            id = prefsobj.getString(ID, null),
            userName = prefsobj.getString(USER_NAME,null)
        )
    }

}
