package com.example.chatmodule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatmodule.databinding.ItemChannelChooserBinding
import com.sendbird.android.AdminMessage
import com.sendbird.android.FileMessage
import com.sendbird.android.GroupChannel
import com.sendbird.android.UserMessage

class ChannelListAdapter(listener: OnChannelClickedListener) : RecyclerView.Adapter<ChannelListAdapter.ChannelHolder>() {
    interface OnChannelClickedListener {
        fun onItemClicked(channel: GroupChannel)
    }

    private val listener: OnChannelClickedListener
    private var channels: MutableList<GroupChannel>


    init {
        channels = ArrayList()
        this.listener = listener
    }

    fun addChannels(channels: MutableList<GroupChannel>) {
        this.channels = channels
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ChannelHolder(DataBindingUtil.inflate(layoutInflater,R.layout.item_channel_chooser, parent, false))    }

    override fun getItemCount() = channels.size


    override fun onBindViewHolder(holder: ChannelHolder, position: Int) {
        holder.bindViews(channels[position], listener)
    }

    class ChannelHolder(binding: ItemChannelChooserBinding) : RecyclerView.ViewHolder(binding.root) {


        val channelName = binding.textChannelName//v.text_channel_name
        val channelRecentMessage = binding.textChannelRecent//v.text_channel_recent
        val channelMemberCount = binding.textChannelMemberCount//v.text_channel_member_count


        fun bindViews(groupChannel: GroupChannel, listener: OnChannelClickedListener) {
            val lastMessage = groupChannel.lastMessage

            if (lastMessage != null) {

                when (lastMessage) {
                    is UserMessage -> channelRecentMessage.setText(lastMessage.message)
                    is AdminMessage -> channelRecentMessage.setText(lastMessage.message)
                    else -> {
                        val fileMessage = lastMessage as FileMessage
                        val sender = fileMessage.sender.nickname

                        channelRecentMessage.text = sender
                    }
                }
            }

            itemView.setOnClickListener {
                listener.onItemClicked(groupChannel)
            }

            channelName.text = groupChannel.members[0].nickname
            channelMemberCount.text = groupChannel.memberCount.toString()
        }

    }

}