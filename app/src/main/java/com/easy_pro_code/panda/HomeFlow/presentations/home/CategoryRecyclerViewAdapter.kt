package com.easy_pro_code.panda.HomeFlow.presentations.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.databinding.CategoriesListItemBinding

class CategoryRecyclerViewAdapter  (val dataList: List<String>?)
    : ListAdapter<String, RecyclerView.ViewHolder>(CategoryDiffUtil())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CategoryViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        val item=getItem(position)
        (holder as CategoryViewHolder).bind(item,onCategoryClickListener)
    }

    var onCategoryClickListener:OnCategoryClickListener?=null


    interface OnCategoryClickListener{
        fun onClick(category: String)
    }


}


class CategoryViewHolder(val binding: CategoriesListItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    companion object{
        fun create(parent: ViewGroup): CategoryViewHolder
        {
            val inflater = LayoutInflater.from(parent.context)
            val binding = CategoriesListItemBinding.inflate(inflater, parent, false)
            return CategoryViewHolder(binding)
        }
    }

    fun bind(
        category: String,
        onCategoryClickListener: CategoryRecyclerViewAdapter.OnCategoryClickListener?
    )
    {
        binding.cateName.setText(category)
        binding.cateCardView.setOnClickListener {
            Log.i("onCategoryClickListener",onCategoryClickListener.toString())
            onCategoryClickListener?.onClick(category)
        }
        binding.executePendingBindings()
    }
}

class CategoryDiffUtil : DiffUtil.ItemCallback<String>()
{
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean
    {
        return oldItem == newItem
    }

}