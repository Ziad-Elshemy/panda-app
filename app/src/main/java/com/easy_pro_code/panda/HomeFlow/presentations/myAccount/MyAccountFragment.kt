package com.easy_pro_code.panda.HomeFlow.presentations.myAccount

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.MainActivity
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentMyAccountBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MyAccountFragment : Fragment() {

    lateinit var binding : FragmentMyAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_account,container,false)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })

        binding.loginBtn.setOnClickListener {
            val action = MyAccountFragmentDirections.actionMyAccountFragment2ToLoginFragment2()
            findNavController().navigate(action)
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=false
        }
        binding.signupBtn.setOnClickListener {
            val action = MyAccountFragmentDirections.actionMyAccountFragment2ToSignUpFragment2()
            findNavController().navigate(action)
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=false
        }
        binding.editAccountBtn.setOnClickListener {
            val action = MyAccountFragmentDirections.actionMyAccountFragment2ToEditMyAccountFragment()
            findNavController().navigate(action)
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=false
        }
        binding.myWalletBtn.setOnClickListener {
            Toast.makeText(requireContext(), "coming soon", Toast.LENGTH_SHORT).show()
//            val action = MyAccountFragmentDirections.actionMyAccountFragment2ToMyWalletFragment()
//            findNavController().navigate(action)
        }
        binding.myOrdersBtn.setOnClickListener {
            val action = MyAccountFragmentDirections.actionMyAccountFragment2ToMyOrdersFragment()
            findNavController().navigate(action)
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=false
        }
        binding.support2Btn.setOnClickListener {
            Toast.makeText(requireContext(), "coming soon", Toast.LENGTH_SHORT).show()
        }
        binding.language2Btn.setOnClickListener {
            Toast.makeText(requireContext(), "coming soon", Toast.LENGTH_SHORT).show()
        }
        binding.termsAndConditionsBtn.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW, Uri.parse("https://easyprocode.com/"))
            startActivity(intent)
        }
        binding.aboutBtn.setOnClickListener {
            val action = MyAccountFragmentDirections.actionMyAccountFragment2ToAboutFragment()
            findNavController().navigate(action)
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=false
        }




        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=true
    }

}