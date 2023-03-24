package com.easy_pro_code.panda.HomeFlow.presentations.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.HomeFlow.models.MyCartModel
import com.easy_pro_code.panda.databinding.ItemCartBinding


class CartRecyclerView (
    val dataList: List<MyCartModel>?)
    : ListAdapter<MyCartModel, RecyclerView.ViewHolder>(CartDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CartViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as CartViewHolder).bind(item)

    }
}
    class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)
    {
        companion object{
            fun create(parent: ViewGroup): CartViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemCartBinding.inflate(inflater, parent, false)
                return CartViewHolder(binding)
            }
        }

        fun bind(cart: MyCartModel)
        {
            //name
            binding.cateName.text=cart.title
            //price
            binding.priceDisplay.text=cart.price
//            binding.executePendingBindings()

        }
    }

    class CartDiffUtil() : DiffUtil.ItemCallback<MyCartModel>(){
        override fun areItemsTheSame(oldItem: MyCartModel, newItem: MyCartModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MyCartModel, newItem: MyCartModel): Boolean {
            return oldItem.userId == newItem.userId
        }

    }