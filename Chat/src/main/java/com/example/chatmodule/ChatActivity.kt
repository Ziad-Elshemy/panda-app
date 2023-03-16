package com.example.chatmodule

import MessageAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sendbird.android.*

class ChatActivity : AppCompatActivity() {
    private val EXTRA_CHANNEL_URL = "EXTRA_CHANNEL_URL"
    private val CHANNEL_HANDLER_ID = "CHANNEL_HANDLER_GROUP_CHANNEL_CHAT"

//    private val our_request_code :Int = 123

    lateinit var titleName:TextView

    private lateinit var adapter: MessageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var groupChannel: GroupChannel
    private lateinit var channelUrl: String

//    fun takePhoto(view: View){
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if (intent.resolveActivity(packageManager) != null){
//            startActivityForResult(intent,our_request_code)
//        }
//
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == our_request_code && resultCode == RESULT_OK){
//
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        titleName=findViewById(R.id.title)

        titleName.text="Driver Final"

        setUpRecyclerView()
        setButtonListeners()
    }

    /**
     * Set up the  recyclerview and set the adapter
     */
    private fun setUpRecyclerView() {
        adapter = MessageAdapter(this)
        recyclerView = findViewById(R.id.recycler_gchat)

        recyclerView.adapter = adapter
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        recyclerView.layoutManager = layoutManager
        recyclerView.scrollToPosition(0)

    }

    /**
     * Function handles setting handlers for back/send button
     */
    private fun setButtonListeners() {

        val back = findViewById<TextView>(R.id.button_gchat_back)
        back.setOnClickListener {
            val intent = Intent(this, ChannelListActivity::class.java)
            startActivity(intent)
        }

//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if (intent.resolveActivity(packageManager) != null){
//            startActivityForResult(intent,our_request_code)
//        }

        val send = findViewById<ImageButton>(R.id.button_gchat_send)
        send.setOnClickListener {
            sendMessage()
        }


    }

    /**
     * Sends the message from the edit text, and clears text field.
     */
    private fun sendMessage()
    {
        val edit_gchat_message = findViewById<EditText>(R.id.edit_gchat_message)
        if (edit_gchat_message.text.isNotEmpty()){
            val params = UserMessageParams()
                .setMessage(edit_gchat_message.text.toString())
            groupChannel.sendUserMessage(params,
                BaseChannel.SendUserMessageHandler { userMessage, e ->
                    if (e != null) {    // Error.
                        return@SendUserMessageHandler
                    }
                    adapter.addFirst(userMessage)
                    edit_gchat_message.text.clear()
                })
        }

    }

    /**
     * Get the Channel URL from the passed intent
     */
    private fun getChannelURl(): String {
        val intent = this.intent

        return intent.getStringExtra(EXTRA_CHANNEL_URL)!!
    }

    /**
     * Function to get previous messages in channel
     */
    private fun getMessages() {

        val previousMessageListQuery = groupChannel.createPreviousMessageListQuery()

        previousMessageListQuery.load(
            100,
            true,
            object : PreviousMessageListQuery.MessageListQueryResult {
                override fun onResult(
                    messages: MutableList<BaseMessage>?,
                    e: SendBirdException?
                ) {
                    if (e != null) {
                        Log.e("Error", e.message.toString())
                    }
                    adapter.loadMessages(messages!!)
                }
            })

    }

    override fun onResume() {
        super.onResume()
        channelUrl = getChannelURl()

        GroupChannel.getChannel(channelUrl,
            GroupChannel.GroupChannelGetHandler { groupChannel, e ->
                if (e != null) {
                    // Error!
                    e.printStackTrace()
                    return@GroupChannelGetHandler
                }
                this.groupChannel = groupChannel
                getMessages()
            })

        SendBird.addChannelHandler(
            CHANNEL_HANDLER_ID,
            object : SendBird.ChannelHandler() {
                override fun onMessageReceived(
                    baseChannel: BaseChannel,
                    baseMessage: BaseMessage
                ){
                    if (baseChannel.url == channelUrl) {
                        // Add new message to view
                        adapter.addFirst(baseMessage)
                        groupChannel.markAsRead()
                    }
                }
            })
    }

    override fun onPause() {
        super.onPause()
        SendBird.removeChannelHandler(CHANNEL_HANDLER_ID)
    }


}