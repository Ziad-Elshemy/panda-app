package com.easy_pro_code.panda.HomeFlow.presentations.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.denzcoskun.imageslider.models.SlideModel
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)

        val imageList = ArrayList<SlideModel>()

        imageList.add(
            SlideModel(
                "https://images.unsplash.com/photo-1583267746897-2cf415887172?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8YXV0b21vYmlsZXxlbnwwfHwwfHw%3D&w=1000&q=80",
            )
        )
        imageList.add(
            SlideModel(
                "https://media.autoexpress.co.uk/image/private/s--bNI-gQV0--/v1675962053/evo/2023/02/Lamborghini%20Urus%202023%20review%20header.jpg",
            )

        )
        imageList.add(
            SlideModel(
                "https://ksa.motory.com/tinymce/lamborghini_urus_by_mansory_and_mtm.jpg",
            )

        )
        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)



        return binding.root
    }

}