package com.easy_pro_code.panda.HomeFlow.presentations.cart

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
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
        (holder as CartViewHolder).bind(item)

        holder.binding.deleteCardView.setOnClickListener(
            View.OnClickListener {
                notifyItemRemoved(position)
            })


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
            binding.priceDisplay.text=cart.price.toString()
            //count
            binding.numberOfProduct.text =cart.count.toString()

            binding.MinusBtn.setOnClickListener(
                View.OnClickListener {

                    if(cart.count!! >= 1) {
                        cart.count = cart.count!! - 1
                        binding.numberOfProduct.text = cart.count.toString()
                        val newPrice = cart.price!! * cart.count!!
                        binding.priceDisplay.text = newPrice.toString()
                    }


                })
            binding.plusIcon.setOnClickListener(
                View.OnClickListener {
                    cart.count = cart.count!! + 1
                    binding.numberOfProduct.text =cart.count.toString()
                    val newPrice = cart.price!! * cart.count!!
                    binding.priceDisplay.text=newPrice.toString()
                })






//          binding.executePendingBindings()

            //spinner Value
//            val number = arrayOf( cart.count.toString() , "1", "2", "3", "4", "5", "6", "7" , "8" , "9" , "10")
//            val spinner = binding.spinnerMenuNumOfItems
//            val arrayAdapter = ArrayAdapter(requireContext(),R.layout.simple_spinner_item, number)
//            spinner.adapter = arrayAdapter
//            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                 //   Toast.makeText(requireContext() ,getString(com.easy_pro_code.panda.R.string.selected_item) + " " + number[position], Toast.LENGTH_SHORT).show()
//                    pos = number[position].toInt()
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                  //  Toast.makeText(requireContext() ,"Please select number of product", Toast.LENGTH_SHORT).show()
//                }
//            }


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

