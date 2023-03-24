package com.easy_pro_code.panda.HomeFlow.presentations.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.HomeFlow.models.fromProductItemToWishProduct
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.local_database.WishProduct
import com.easy_pro_code.panda.databinding.ProductItemBinding

class ProductsHomeRecyclerView(
    val dataList: List<Product>?,
    var wishList: List<WishProduct>?
)
    : ListAdapter<Product, RecyclerView.ViewHolder>(ProductDiffUtil())
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            return ProductViewHolder.create(parent)
        }


        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
        {
            val item=getItem(position)
            (holder as ProductViewHolder).bind(item,onProductClickListener,wishList)
        }

        var onProductClickListener:OnProductClickListener?=null


        interface OnProductClickListener{
            fun onClick(product: Product)
            fun onCheck(product: Product)
            fun onUnCheck(product: Product)
        }

        fun submitWishList(wishList: List<WishProduct>?){
            this.wishList=wishList
            notifyDataSetChanged()
        }


    }


    class ProductViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        companion object{
            fun create(parent: ViewGroup): ProductViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ProductItemBinding.inflate(inflater, parent, false)
                return ProductViewHolder(binding)
            }
        }

        fun bind(
            product: Product,
            onProductClickListener: ProductsHomeRecyclerView.OnProductClickListener?,
            wishList: List<WishProduct>?
        )
        {
            binding.productName.setText(product.title)
            binding.productPrice.setText(product.price)
            binding.frame2.setOnClickListener {
                onProductClickListener?.onClick(product)
            }
            if (wishList != null) {
                if (product.fromProductItemToWishProduct() in wishList){
                    binding.favIcon.isChecked=true
                }
            }
            binding.favIcon.setOnCheckedChangeListener{
                checkBox,isChecked->
                Log.i("isChecked now:",isChecked.toString())
                if (isChecked) onProductClickListener?.onCheck(product)
                else onProductClickListener?.onUnCheck(product)
            }

            binding.executePendingBindings()


        }
    }


    class ProductDiffUtil : DiffUtil.ItemCallback<Product>()
    {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean
        {
            return oldItem == newItem
        }



    }



