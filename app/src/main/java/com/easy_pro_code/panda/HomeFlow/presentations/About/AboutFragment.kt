package com.easy_pro_code.panda.HomeFlow.presentations.About

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {
    lateinit var binding: FragmentAboutBinding
//    private val reviewsViewModel: ReviewsViewModel by viewModels()
//    private val settingsViewModel: SettingsViewModel by viewModels()
//    private val aboutViewModel: AboutViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)

        return binding.root
    }

}