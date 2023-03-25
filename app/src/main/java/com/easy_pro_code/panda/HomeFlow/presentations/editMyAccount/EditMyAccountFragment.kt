package com.easy_pro_code.panda.HomeFlow.presentations.editMyAccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.databinding.FragmentEditMyAccountBinding


class EditMyAccountFragment : Fragment() {
    lateinit var binding:FragmentEditMyAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_my_account,container,false)

        binding.myAccountName.setText(AuthUtils.manager.fetchData().userName)

        binding.name.setText(AuthUtils.manager.fetchData().userName)
        binding.name.isEnabled = false

        binding.phone.setText(AuthUtils.manager.getPhone())
        binding.phone.isEnabled = false

        binding.email.setText(AuthUtils.manager.fetchData().email)
        binding.email.isEnabled = false

        binding.password.setText("*************")
        binding.password.isEnabled = false

        binding.doneBtn.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }

}