package com.easy_pro_code.panda.HomeFlow.presentations.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.databinding.VariantColorItemBinding

class VariantColorRecyclerViewAdapter
    : ListAdapter<String, RecyclerView.ViewHolder>(VariantColorDiffUtil())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return VariantColorViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        val item=getItem(position)
        (holder as VariantColorViewHolder).bind(item,onVariantColorClickListener)
    }

    var onVariantColorClickListener:OnVariantColorClickListener?=null


    interface OnVariantColorClickListener{
        fun onClick(color: String)
    }



}


class VariantColorViewHolder(val binding: VariantColorItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    companion object{
        fun create(parent: ViewGroup): VariantColorViewHolder
        {
            val inflater = LayoutInflater.from(parent.context)
            val binding = VariantColorItemBinding.inflate(inflater, parent, false)
            return VariantColorViewHolder(binding)
        }
    }

    fun bind(
        color: String,
        onVariantColorClickListener: VariantColorRecyclerViewAdapter.OnVariantColorClickListener?
    )
    {
        binding.color.setOnClickListener{
            //change color
            onVariantColorClickListener?.onClick(color)
        }
    }
}


class VariantColorDiffUtil : DiffUtil.ItemCallback<String>()
{
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean
    {
        return oldItem == newItem
    }



}



