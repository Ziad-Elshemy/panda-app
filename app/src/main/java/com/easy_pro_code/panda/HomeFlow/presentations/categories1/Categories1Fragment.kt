package com.easy_pro_code.panda.HomeFlow.presentations.categories1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentCategories1Binding


class Categories1Fragment : Fragment() {

    lateinit var binding:FragmentCategories1Binding

    lateinit var recyclerCategories:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_categories1,container,false)


        return binding.root
    }

}