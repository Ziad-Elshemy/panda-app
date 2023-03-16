package com.easy_pro_code.panda.AuthFlow.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.HomeFlow.HomeActivity
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    lateinit var binding:FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)

        binding.skip.setOnClickListener {
            val intent = Intent(requireContext(),HomeActivity::class.java)
            startActivity(intent)

        }

        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_otpFragment)
        }

        return binding.root
    }

}