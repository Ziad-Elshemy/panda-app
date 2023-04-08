package com.easy_pro_code.panda.HomeFlow.presentations.home

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.HomeFlow.models.Phone
import com.easy_pro_code.panda.HomeFlow.models.fromProductItemToWishProduct
import com.easy_pro_code.panda.data.Models.local_database.WishProduct
import com.easy_pro_code.panda.databinding.PhonesListItemBinding

class PhonesRecyclerView (val dataList: List<Phone>?,var wishList: List<WishProduct>?)
: ListAdapter<Phone, RecyclerView.ViewHolder>(PhoneDiffUtil())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PhoneViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        val item=getItem(position)
        (holder as PhoneViewHolder).bind(item,onPhoneClickListener,wishList)
    }

    var onPhoneClickListener:OnPhoneClickListener?=null


    interface OnPhoneClickListener{
        fun onClick(phone: Phone)
        fun onCheck(phone: Phone)
        fun onUnCheck(phone: Phone)
    }

    fun submitWishList(wishList: List<WishProduct>?){
        this.wishList=wishList
    }
}


class PhoneViewHolder(val binding: PhonesListItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    companion object{
        fun create(parent: ViewGroup): PhoneViewHolder
        {
            val inflater = LayoutInflater.from(parent.context)
            val binding = PhonesListItemBinding.inflate(inflater, parent, false)
            return PhoneViewHolder(binding)
        }
    }

    fun bind(
        phone: Phone,
        onPhoneClickListener: PhonesRecyclerView.OnPhoneClickListener?,
        wishList: List<WishProduct>?
    )
    {


        try {
            val base64String = phone.product.image
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.phoneItemImage.setImageBitmap(decodedByte)
        }
        catch (E:Exception){
            Log.i("Mokhtar",  phone.product.image.toString())

        }

        binding.phoneName.setText(phone.product.title)
        binding.phonePrice.setText(phone.product.price)
        binding.frame2.setOnClickListener{
            onPhoneClickListener?.onClick(phone)
        }
        binding.phoneItemImage.setOnClickListener {
            onPhoneClickListener?.onClick(phone)
        }
        if (wishList != null) {
            if (phone.product.fromProductItemToWishProduct() in wishList){
                binding.favIcon.isChecked=true
            }
        }
        binding.favIcon.setOnCheckedChangeListener{
                checkBox,isChecked->
            if (isChecked) onPhoneClickListener?.onCheck(phone)
            else onPhoneClickListener?.onUnCheck(phone)
        }
        binding.rate1.setText(phone.product.rate.toString())
        binding.rate2.setText(phone.product.rate.toString())

        binding.executePendingBindings()
    }
}

class PhoneDiffUtil : DiffUtil.ItemCallback<Phone>()
{
    override fun areItemsTheSame(oldItem: Phone, newItem: Phone): Boolean {
        return oldItem.product.id == newItem.product.id
    }

    override fun areContentsTheSame(oldItem: Phone, newItem: Phone): Boolean
    {
        return oldItem == newItem
    }

}