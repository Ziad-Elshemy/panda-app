package com.easy_pro_code.panda.HomeFlow.presentations.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.databinding.VariantOtherItemBinding

class VariantOtherRecyclerViewAdapter(val map: HashMap<String,String>) : RecyclerView.Adapter<VariantOtherViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariantOtherViewHolder {

        return VariantOtherViewHolder.create(parent)
    }

    override fun getItemCount(): Int =map.size

    val keys=map.keys.toMutableList()

    override fun onBindViewHolder(holder: VariantOtherViewHolder, position: Int)
    {
        val key=keys.get(position)
        val item=map.get(key)
        (holder).bind(item!!,onVariantOtherClickListener,key)
    }

    var onVariantOtherClickListener:OnVariantOtherClickListener?=null


    interface OnVariantOtherClickListener{
        fun onClick(Other: String)
    }



}


class VariantOtherViewHolder(val binding: VariantOtherItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    companion object{
        fun create(parent: ViewGroup): VariantOtherViewHolder
        {
            val inflater = LayoutInflater.from(parent.context)
            val binding = VariantOtherItemBinding.inflate(inflater, parent, false)
            return VariantOtherViewHolder(binding)
        }
    }

    fun bind(
        item: String,
        onVariantOtherClickListener: VariantOtherRecyclerViewAdapter.OnVariantOtherClickListener?,
        key: String
    )
    {
        binding.key.setText(key)
        binding.value.setText(item)
    }
}
