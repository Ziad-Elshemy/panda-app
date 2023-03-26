package com.easy_pro_code.panda.HomeFlow.presentations.home

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.HomeFlow.models.Offer
import com.easy_pro_code.panda.databinding.OffersListItemBinding

class OffersRecyclerView (val dataList: List<Offer>?)
: ListAdapter<Offer, RecyclerView.ViewHolder>(OfferDiffUtil())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return OfferViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        val item=getItem(position)
        (holder as OfferViewHolder).bind(item,onOfferClickListener)
    }

    var onOfferClickListener:OnOfferClickListener?=null


    interface OnOfferClickListener{
        fun onClick(offer: Offer)
        fun onCheck(offer: Offer)
        fun onUnCheck(offer: Offer)
    }


}


class OfferViewHolder(val binding: OffersListItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    companion object{
        fun create(parent: ViewGroup): OfferViewHolder
        {
            val inflater = LayoutInflater.from(parent.context)
            val binding = OffersListItemBinding.inflate(inflater, parent, false)
            return OfferViewHolder(binding)
        }
    }

    fun bind(offer: Offer, onOfferClickListener: OffersRecyclerView.OnOfferClickListener?)
    {


        try {
            val base64String = offer.product.image
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            binding.driverInfoImage.setImageBitmap(decodedByte)
        }
        catch (E:Exception){
            Log.i("Mokhtar",  offer.product.image.toString())

        }

        binding.offerName.setText(offer.product.title)
        binding.offerPrice.setText(offer.newPrice)
        binding.offerOldPrice.setText(offer.product.price)
        binding.offerOldPrice.paintFlags = binding.offerOldPrice.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
        binding.frame2.setOnClickListener{
            onOfferClickListener?.onClick(offer)
        }
        binding.driverInfoImage.setOnClickListener {
            onOfferClickListener?.onClick(offer)
        }
//        binding.favIcon.setOnCheckedChangeListener{
//                checkBox,isChecked->
//            if (isChecked) onOfferClickListener?.onCheck(offer)
//            else onOfferClickListener?.onUnCheck(offer)
//        }
        binding.rate1.setText(offer.product.rate.toString())
        binding.rate2.setText(offer.product.rate.toString())

        binding.executePendingBindings()
    }
}

class OfferDiffUtil : DiffUtil.ItemCallback<Offer>()
{
    override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
        return oldItem.product.id == newItem.product.id
    }

    override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean
    {
        return oldItem == newItem
    }

}