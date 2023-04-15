package com.easy_pro_code.panda.HomeFlow.presentations.MyAddress

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.databinding.AddressListItemBinding
import java.util.*


class MyAddressAdapter: RecyclerView.Adapter<AddressItemViewHolder>()
{
    val addressListData: LinkedList<AddressItem> = LinkedList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressItemViewHolder
    {
        return AddressItemViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return addressListData.size
    }

    override fun onBindViewHolder(holder: AddressItemViewHolder, position: Int)
    {
        holder.bind(addressListData[position])
    }
    fun add(addressItem: AddressItem)
    {
        addressListData.add(addressItem)

        Log.i("Felo", "${addressListData.size}")
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

    fun bind(item: AddressItem)
    {
        binding.address.text = item.address
        binding.phoneNum.text = item.phone
    }
}

data class AddressItem(
    val address: String,
    val phone: String
)


