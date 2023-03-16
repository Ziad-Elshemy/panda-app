package com.example.chatmodule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sendbird.android.GroupChannel
import com.sendbird.android.GroupChannelParams
import com.sendbird.android.SendBird
import com.sendbird.android.User

class ChannelCreateActivity : AppCompatActivity()/*, ChannelCreateAdapter.OnItemCheckedChangeListener */{

    private val EXTRA_CHANNEL_URL = "EXTRA_CHANNEL_URL"

    private val myUserId:String = "01286137413"
    private val myUserName:String = "Support"



    private lateinit var selectedUsers: ArrayList<String>
//    private lateinit var adapter: ChannelCreateAdapter
    private lateinit var recyclerView: RecyclerView

    lateinit var button_create:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel_create)

        SendBird.init("6CFE4CBC-7194-4D4B-9F69-468564C96B5D", this)


//        Log.e("test","h")
        connectToSendBird(myUserId,myUserName)

        selectedUsers = ArrayList()


//        adapter = ChannelCreateAdapter(this)
//        recyclerView = findViewById(R.id.recycler_create)
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)

        button_create = findViewById(R.id.button_create)

        loadUsers()
        button_create.setOnClickListener { createChannel(selectedUsers)}

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
//            adapter.addChannels(list)
        }
    }


    private fun loadUsers() {
        val userListQuery = SendBird.createApplicationUserListQuery()

        userListQuery.next() { list, e ->
            if (e != null) {
                Log.e("TAG", e.message.toString())
            } else {
//                adapter.addUsers(list)
                selectedUsers.add("640f7d7faf76d50d0e6eb811")
                selectedUsers.add(myUserId)
            }
        }
    }

    private fun createChannel(users: MutableList<String>) {
        val params = GroupChannelParams()

        val operatorId = ArrayList<String>()
        operatorId.add("640f7d7faf76d50d0e6eb811")
//        operatorId.add(ourUserId)
     params.setDistinct(true)
        params.setName("Driver Final");
        params.addUserIds(users)
        params.setOperatorUserIds(operatorId)

        GroupChannel.createChannel(params) { groupChannel, e ->
            if (e != null) {
                Log.e("TAG", e.message.toString())
            } else {
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra(EXTRA_CHANNEL_URL, groupChannel.url)
                startActivity(intent)
            }
        }
    }

//    override fun onItemChecked(user: User, checked: Boolean) {
//        if (checked) {
//            selectedUsers.add(user.userId)
////            selectedUsers.add(ourUserId)
//        } else {
//            selectedUsers.remove(user.userId)
////            selectedUsers.remove(ourUserId)
//        }
//    }
}