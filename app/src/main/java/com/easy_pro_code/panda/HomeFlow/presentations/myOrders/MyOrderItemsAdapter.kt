package com.easy_pro_code.panda.HomeFlow.presentations.myOrders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.data.Models.remote_backend.OrderItemsItem
import com.easy_pro_code.panda.databinding.OrdersListItemBinding
import java.time.LocalDate


class MyOrderItemsAdapter(val dataList: List<String>?, val hashMap: HashMap<String, List<OrderItemsItem?>>)
    
    : ListAdapter<String, RecyclerView.ViewHolder>(StringDiffUtil())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return OrderItemViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        val item=getItem(position)
        hashMap.get(item).let {
            (holder as OrderItemViewHolder).bind(item,onOrderItemClickListener,position ,
                it
            )
        }
    }

    var onOrderItemClickListener:OnOrderItemClickListener?=null


    interface OnOrderItemClickListener{
        fun onClick(order: String)
    }


}


class OrderItemViewHolder(val binding: OrdersListItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    companion object{
        fun create(parent: ViewGroup): OrderItemViewHolder
        {
            val inflater = LayoutInflater.from(parent.context)
            val binding = OrdersListItemBinding.inflate(inflater, parent, false)
            return OrderItemViewHolder(binding)
        }
    }

    fun bind(
        order: String,
        onOrderItemClickListener: MyOrderItemsAdapter.OnOrderItemClickListener?,
        position: Int,
        orderItemsItems: List<OrderItemsItem?>?
    )
    {
        binding.DateTv.setText(LocalDate.now().toString())
        binding.itemsNumTextView.setText(orderItemsItems?.size.toString())
        binding.orderNumTextView.setText((position + 1).toString())
        binding.orderItemCard.setOnClickListener {
            onOrderItemClickListener?.onClick(order)
        }
        binding.executePendingBindings()
    }
}


class StringDiffUtil : DiffUtil.ItemCallback<String>()
{
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean
    {
        return oldItem == newItem
    }



}


