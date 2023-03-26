package com.easy_pro_code.panda.HomeFlow.presentations.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.databinding.VariantSizeItemBinding

class VariantSizeRecyclerViewAdapter : ListAdapter<String, RecyclerView.ViewHolder>(VariantSizeDiffUtil())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return VariantSizeViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        val item=getItem(position)
        (holder as VariantSizeViewHolder).bind(item,onVariantSizeClickListener)
    }

    var onVariantSizeClickListener:OnVariantSizeClickListener?=null


    interface OnVariantSizeClickListener{
        fun onClick(size: String)
    }



}


class VariantSizeViewHolder(val binding: VariantSizeItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    companion object{
        fun create(parent: ViewGroup): VariantSizeViewHolder
        {
            val inflater = LayoutInflater.from(parent.context)
            val binding = VariantSizeItemBinding.inflate(inflater, parent, false)
            return VariantSizeViewHolder(binding)
        }
    }

    fun bind(
        size: String,
        onVariantSizeClickListener: VariantSizeRecyclerViewAdapter.OnVariantSizeClickListener?
    )
    {
        binding.sizeName.setOnClickListener{
            //change Size
            onVariantSizeClickListener?.onClick(size)
        }

        binding.sizeName.setText(size)
    }
}


class VariantSizeDiffUtil : DiffUtil.ItemCallback<String>()
{
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean
    {
        return oldItem == newItem
    }
}