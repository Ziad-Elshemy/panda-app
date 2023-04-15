package com.easy_pro_code.panda.HomeFlow.presentations.MyAddress

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.databinding.AddressListItemBinding
import java.util.*


class MyAddressAdapter: RecyclerView.Adapter<AddressItemViewHolder>()
{
    val addressListData: LinkedList<AddressItem?>? = LinkedList()
    var addressListDataShared: LinkedList<AddressItem?>? = AuthUtils.manager.getArrayList("Address")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressItemViewHolder
    {
        return AddressItemViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
//        return addressListData.size
        return addressListDataShared?.size?:0
    }

    override fun onBindViewHolder(holder: AddressItemViewHolder, position: Int)
    {
//        holder.bind(addressListData[position])
        holder.bind(addressListDataShared?.get(position)!!,position)
    }
    fun add(addressItem: AddressItem)
    {
        addressListData?.add(addressItem)
        addressListDataShared?.add(addressItem)
        AuthUtils.manager.saveArrayList(addressListData,"Address")
        addressListDataShared = AuthUtils.manager.getArrayList("Address")
        Log.i("Felo", "${addressListData.toString()}")
        notifyDataSetChanged()
    }
}

class AddressItemDiffUtil : DiffUtil.ItemCallback<AddressItem>()
{
    override fun areItemsTheSame(oldItem: AddressItem, newItem: AddressItem): Boolean
    {
        return oldItem.phone === newItem.phone
    }

    override fun areContentsTheSame(oldItem: AddressItem, newItem: AddressItem): Boolean
    {
        return oldItem.phone == newItem.phone
    }

}

class AddressItemViewHolder(val binding: AddressListItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    companion object{
        fun create(parent: ViewGroup): AddressItemViewHolder
        {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = AddressListItemBinding.inflate(layoutInflater, parent, false)
            return AddressItemViewHolder(binding)
        }
    }

    fun bind(item: AddressItem,position: Int)
    {
        binding.addressNum.text = (position+1).toString()
        binding.address.text = item.address
        binding.phoneNum.text = item.phone
    }
}

data class AddressItem(
    val address: String = "s",
    val phone: String = "s"
)


