package com.example.chatmodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.chatmodule.databinding.ActivityLoginBinding
import com.sendbird.android.SendBird

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    lateinit var loginButton :Button
    private val ourUserId:String = ""
    private val userName:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SendBird.init("29ACA00D-DDD8-4C54-B5CD-4EB5E82EFDAD", this)

        loginButton = binding.buttonLoginConnect

        loginButton.setOnClickListener {
            connectToSendBird(ourUserId,userName)
        }
    }

    private fun connectToSendBird(userID: String, userName: String) {
        SendBird.connect(userID) { user, e ->
            if (e != null) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            } else {
                SendBird.updateCurrentUserInfo(userName, null) { e ->
                    if (e != null) {
                        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    }
                    val intent = Intent(this, ChannelListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}