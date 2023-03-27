package com.easy_pro_code.panda.HomeFlow.presentations.home

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.HomeFlow.models.Electronics
import com.easy_pro_code.panda.databinding.ElectronicsListItemBinding

class ElectronicsRecyclerView (val dataList: List<Electronics>?)
: ListAdapter<Electronics, RecyclerView.ViewHolder>(ElectronicDiffUtil())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ElectronicViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        val item=getItem(position)
        (holder as ElectronicViewHolder).bind(item,onElectronicClickListener)
    }

    var onElectronicClickListener:OnElectronicClickListener?=null


    interface OnElectronicClickListener{
        fun onClick(electronic: Electronics)
        fun onCheck(electronic: Electronics)
        fun onUnCheck(electronic: Electronics)
    }


}


class ElectronicViewHolder(val binding: ElectronicsListItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    companion object{
        fun create(parent: ViewGroup): ElectronicViewHolder
        {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ElectronicsListItemBinding.inflate(inflater, parent, false)
            return ElectronicViewHolder(binding)
        }
    }

    fun bind(electronic: Electronics, onElectronicClickListener: ElectronicsRecyclerView.OnElectronicClickListener?)
    {



        try {
            val base64String = electronic.product.image
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.electronicItemImage.setImageBitmap(decodedByte)
        }
        catch (E:Exception){
            Log.i("Mokhtar",  electronic.product.image.toString())

        }

        binding.electronicName.setText(electronic.product.title)
        binding.electronicPrice.setText(electronic.product.price)
        binding.frame2.setOnClickListener{
            onElectronicClickListener?.onClick(electronic)
        }
        binding.electronicItemImage.setOnClickListener {
            onElectronicClickListener?.onClick(electronic)
        }
        binding.favIcon.setOnCheckedChangeListener{
                checkBox,isChecked->
            if (isChecked) onElectronicClickListener?.onCheck(electronic)
            else onElectronicClickListener?.onUnCheck(electronic)
        }
        binding.rate1.setText(electronic.product.rate.toString())
        binding.rate2.setText(electronic.product.rate.toString())

        binding.executePendingBindings()
    }
}

class ElectronicDiffUtil : DiffUtil.ItemCallback<Electronics>()
{
    override fun areItemsTheSame(oldItem: Electronics, newItem: Electronics): Boolean {
        return oldItem.product.id == newItem.product.id
    }

    override fun areContentsTheSame(oldItem: Electronics, newItem: Electronics): Boolean
    {
        return oldItem == newItem
    }

}