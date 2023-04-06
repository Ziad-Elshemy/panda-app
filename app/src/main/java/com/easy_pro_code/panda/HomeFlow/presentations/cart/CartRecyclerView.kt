package com.easy_pro_code.panda.HomeFlow.presentations.cart

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.HomeFlow.models.MyCartModel
import com.easy_pro_code.panda.databinding.ItemCartBinding

var pos:Int = 0

class CartRecyclerView (val dataList: List<MyCartModel>?)
    : ListAdapter<MyCartModel, RecyclerView.ViewHolder>(CartDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CartViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as CartViewHolder).bind(item,itemCounterChangeListener)

        holder.binding.deleteCardView.setOnClickListener(
            View.OnClickListener {
                notifyItemRemoved(position)
            })


    }

    var itemCounterChangeListener:ItemCounterChangeListener?=null

    interface ItemCounterChangeListener{
        fun onCounterButtonsClickListener(
            newCount: Int,
            newPrice: Int,
            priceDisplay: TextView,
            numberOfProduct: TextView,
            cart: MyCartModel
        )
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

        fun bind(
            cart: MyCartModel,
            itemCounterChangeListener: CartRecyclerView.ItemCounterChangeListener?
        )
        {

            try {
                val base64String = cart.image
                val decodedString = Base64.decode(base64String, Base64.DEFAULT)
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                binding.driverInfoImage.setImageBitmap(decodedByte)
            }
            catch (E:Exception){
                Log.i("ABANOUB",cart.image.toString())

            }
            //name
            binding.cateName.text=cart.title
            //price
            val priceTotal = cart.price!! * cart.count!!
            binding.priceDisplay.text="YER ${priceTotal}.00"
            //count
            binding.numberOfProduct.text =cart.count.toString()
            // -
            binding.MinusBtn.setOnClickListener(
                View.OnClickListener {

                    if(cart.count!! >= 1) {
//                        cart.count = cart.count!! - 1

//                        binding.numberOfProduct.text = cart.count.toString()

                        val newPrice = cart.price!! * (cart.count!!-1)
                        itemCounterChangeListener?.onCounterButtonsClickListener(
                            -1,
                            newPrice,
                            binding.priceDisplay,
                            binding.numberOfProduct,
                            cart
                        )
//                        binding.priceDisplay.text = "YER ${newPrice}.00"
                    }


                })
            // +
            binding.plusIcon.setOnClickListener {

//                    cart.count = cart.count!! + 1
//                    binding.numberOfProduct.text =cart.count.toString()
                    val newPrice = cart.price!! * (cart.count!!+1)
                    itemCounterChangeListener?.onCounterButtonsClickListener(
                        1,
                        newPrice,
                        binding.priceDisplay,
                        binding.numberOfProduct,
                        cart
                    )

//                    binding.priceDisplay.text="YER ${newPrice}.00"
                }
//          binding.executePendingBindings()



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


