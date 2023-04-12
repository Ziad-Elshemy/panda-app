package com.easy_pro_code.panda.HomeFlow.presentations.home

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.HomeFlow.models.Product
import com.easy_pro_code.panda.HomeFlow.models.fromProductItemToWishProduct
import com.easy_pro_code.panda.data.Models.local_database.WishProduct
import com.easy_pro_code.panda.databinding.ProductItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BestSellerRecyclerViewAdapter (val dataList: List<Product>?,
                                     var wishList: List<WishProduct>?,
                                     val lifecycleScope: LifecycleCoroutineScope
)
    : ListAdapter<Product, RecyclerView.ViewHolder>(BestSellerDiffUtil())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return BestSellerViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        val item=getItem(position)
        (holder as BestSellerViewHolder).bind(item,onBestSellerClickListener,wishList,lifecycleScope)
    }

    override fun getItemCount(): Int {
        return if (currentList.size<20) currentList.size else 20
    }

    var onBestSellerClickListener:OnBestSellerClickListener?=null


    interface OnBestSellerClickListener{
        fun onClick(product: Product)
        fun onCheck(product: Product)
        fun onUnCheck(product: Product)
    }

    fun submitWishList(wishList: List<WishProduct>?){
        this.wishList=wishList
    }


}


class BestSellerViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    companion object{
        fun create(parent: ViewGroup): BestSellerViewHolder
        {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ProductItemBinding.inflate(inflater, parent, false)
            return BestSellerViewHolder(binding)
        }
    }

    fun bind(
        product: Product,
        onBestSellerClickListener: BestSellerRecyclerViewAdapter.OnBestSellerClickListener?,
        wishList: List<WishProduct>?,
        lifecycleScope: LifecycleCoroutineScope
    )
    {
        lifecycleScope.launch(Dispatchers.Default) {
            try {
                val base64String = product.image
                val decodedString = Base64.decode(base64String, Base64.DEFAULT)
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                withContext(Dispatchers.Main){
                    binding.driverInfoImage.setImageBitmap(decodedByte)
                }
            }
            catch (E:Exception){
                Log.i("ABANOUB",product.image.toString())

            }
        }


        binding.productName.setText(product.title)
        binding.productPrice.setText(product.price)
        binding.frame2.setOnClickListener {
            onBestSellerClickListener?.onClick(product)
        }
        if (wishList != null) {
            if (product.fromProductItemToWishProduct() in wishList){
                binding.favIcon.isChecked=true
            }
        }
        binding.favIcon.setOnCheckedChangeListener{
                checkBox,isChecked->
            Log.i("isChecked now:",isChecked.toString())
            if (isChecked) onBestSellerClickListener?.onCheck(product)
            else onBestSellerClickListener?.onUnCheck(product)
        }

        binding.driverInfoImage.setOnClickListener {
            onBestSellerClickListener?.onClick(product)
        }
        binding.rate1.setText(product.rate.toString())
        binding.rate2.setText(product.rate.toString())
        binding.executePendingBindings()
    }
}


class BestSellerDiffUtil : DiffUtil.ItemCallback<Product>()
{
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean
    {
        return oldItem == newItem
    }



}