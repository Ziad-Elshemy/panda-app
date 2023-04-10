package com.easy_pro_code.panda.HomeFlow.presentations.myOrders

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.data.Models.remote_backend.OrderItemsItem
import com.easy_pro_code.panda.databinding.MyOrdersListItemBinding



class MyOrdersAdapter(val click:ReviewButtonClickListener,val orderList:List<OrderItemsItem?>) : ListAdapter<OrderItemsItem,RecyclerView.ViewHolder>(OrderItemsDiffUtil())  {




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

    fun bind(orderModel:OrderItemsItem,click:ReviewButtonClickListener){

        binding.myOrderName.text=orderModel.productId?.title
        binding.myOrderPrice.text=orderModel.productId?.price
        binding.completedTv.text=orderModel.productId?.category

        val base64String = orderModel.productId?.image
        val decodedString = Base64.decode(base64String, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        binding.myOrderImage.setImageBitmap(decodedByte)

        binding.reviewBtn.setOnClickListener {
            click.clickListener(orderModel)
        }
    }

}


class OrderItemsDiffUtil : DiffUtil.ItemCallback<OrderItemsItem>()
{
    override fun areItemsTheSame(oldItem: OrderItemsItem, newItem: OrderItemsItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: OrderItemsItem, newItem: OrderItemsItem): Boolean
    {
        return oldItem.id == newItem.id
    }

}

class ReviewButtonClickListener(val clickListener : (order:OrderItemsItem)->Unit){

    fun onClick(order:OrderItemsItem){

        clickListener(order)
    }

}

