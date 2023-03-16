package com.example.chatmodule
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sendbird.android.GroupChannel
import android.widget.Button
import android.widget.Toast
import com.example.chatmodule.databinding.ActivityLoginBinding
import com.sendbird.android.SendBird

class ChannelListActivity : AppCompatActivity(),ChannelListAdapter.OnChannelClickedListener {

    private val EXTRA_CHANNEL_URL = "EXTRA_CHANNEL_URL"
    lateinit var adapter: ChannelListAdapter

    lateinit var recyclerView : RecyclerView
    lateinit var fab_group:FloatingActionButton
    lateinit var binding:ActivityLoginBinding
    lateinit var loginButton :Button
    private val ourUserId:String = ""
    private val userName:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        SendBird.init("6CFE4CBC-7194-4D4B-9F69-468564C96B5D", this)
//        Log.e("test","h")
           connectToSendBird("01286137413","Support")
//        Log.e("test","h")



//        loginButton = binding.buttonLoginConnect
//
//        loginButton.setOnClickListener {
//            connectToSendBird(ourUserId,userName)
//        }


        val handler = Handler()

        val updateTask: Runnable = object : Runnable {
            override fun run() {
                handler.postDelayed(this, 1000)
            }
        }

        handler.postDelayed(updateTask, 1000)

        adapter = ChannelListAdapter(this)
        recyclerView = findViewById(R.id.recycler_group_channels)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

//        fab_group = findViewById(R.id.fab_group_channel_create)

//        fab_group.setOnClickListener{
//            val intent = Intent(this, ChannelCreateActivity::class.java)
//            startActivity(intent)
//        }

    }
    private fun connectToSendBird(userID: String, userName: String) {
        SendBird.connect(userID) { user, e ->
            if (e != null) {
               // Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            } else {
                SendBird.updateCurrentUserInfo(userName, null) { e ->
                    if (e != null) {
                 //       Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    }


                    addChannels()
                   // finish()


                }
            }
        }
    }
    private fun addChannels() {
        val channelList = GroupChannel.createMyGroupChannelListQuery()
        channelList.limit = 100
        channelList.next { list, e ->
        //    Log.e("TAG", list.toString())

            if (e != null) {
                Log.e("TAG", e.message.toString())
            }
            adapter.addChannels(list)
        }
    }

    override fun onItemClicked(channel: GroupChannel) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra(EXTRA_CHANNEL_URL, channel.url)
        startActivity(intent)
    }
}

