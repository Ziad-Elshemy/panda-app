package com.easy_pro_code.panda.HomeFlow.presentations.home

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
        (holder as OfferViewHolder).bind(item)
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

    fun bind(offer: Offer)
    {
        binding.offerName.setText(offer.product.title)
        binding.offerPrice.setText(offer.newPrice)
        binding.offerOldPrice.setText(offer.product.price)
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