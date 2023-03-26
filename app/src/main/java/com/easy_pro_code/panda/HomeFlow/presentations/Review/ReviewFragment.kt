package com.easy_pro_code.panda.HomeFlow.presentations.Review

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentReviewBinding


class ReviewFragment : Fragment() {

    lateinit var binding: FragmentReviewBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_review,container,false)

        binding.submitBtn.setOnClickListener {

            Toast.makeText(requireContext(),"Coming soon",Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }


        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        return binding.root
    }


}