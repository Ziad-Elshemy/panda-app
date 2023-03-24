package com.easy_pro_code.panda.HomeFlow.presentations.product_search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.databinding.ProductSearchItemBinding

class ProductSearchRecyclerViewAdapter (
    val dataList: List<Product>?
)
    : ListAdapter<Product, RecyclerView.ViewHolder>(ProductSearchDiffUtil())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ProductSearchViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        val item=getItem(position)
        (holder as ProductSearchViewHolder).bind(item,onProductSearchClickListener)
    }

    var onProductSearchClickListener:OnProductSearchClickListener?=null


    interface OnProductSearchClickListener{
        fun onClick(product: Product)
    }


}


class ProductSearchViewHolder(val binding: ProductSearchItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    companion object{
        fun create(parent: ViewGroup): ProductSearchViewHolder
        {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ProductSearchItemBinding.inflate(inflater, parent, false)
            return ProductSearchViewHolder(binding)
        }
    }

    fun bind(
        product: Product,
        onProductClickListener: ProductSearchRecyclerViewAdapter.OnProductSearchClickListener?
    )
    {
        binding.txtProduct.setText(product.title)
        binding.item.setOnClickListener {
            onProductClickListener?.onClick(product)
        }
        binding.executePendingBindings()
    }
}


class ProductSearchDiffUtil : DiffUtil.ItemCallback<Product>()
{
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean
    {
        return oldItem == newItem
    }



}



