package com.easy_pro_code.panda.HomeFlow.presentations.myOrders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.HomeFlow.models.Order
import com.easy_pro_code.panda.databinding.MyOrdersListItemBinding
import okio.ByteString.Companion.decodeBase64

class MyOrdersAdapter(val click:ReviewButtonClickListener,val orderList:List<Order>) : ListAdapter<Order,RecyclerView.ViewHolder>(OrdersDiffUtil())  {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OrderViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as OrderViewHolder).bind(item,click)
    }
}


class OrderViewHolder(val binding:MyOrdersListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object{
        fun create(parent: ViewGroup): OrderViewHolder
        {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MyOrdersListItemBinding.inflate(inflater, parent, false)
            return OrderViewHolder(binding)
        }
    }

    fun bind(orderModel:Order,click:ReviewButtonClickListener){
//        binding.myOrderImage.setImageResource(orderModel.imageId.toInt())
        binding.myOrderName.text=orderModel.name
        binding.myOrderPrice.text=orderModel.price
        binding.completedTv.text=orderModel.completed

        binding.reviewBtn.setOnClickListener {
            click.clickListener(orderModel)
        }
    }

}


class OrdersDiffUtil : DiffUtil.ItemCallback<Order>()
{
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean
    {
        return oldItem.id == newItem.id
    }

}

class ReviewButtonClickListener(val clickListener : (order:Order)->Unit){

    fun onClick(order:Order){

        clickListener(order)
    }

}

