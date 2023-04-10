package com.easy_pro_code.panda.HomeFlow.presentations.myAccount

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.MainActivity
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.databinding.FragmentMyAccountBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MyAccountFragment : Fragment() {

    lateinit var binding : FragmentMyAccountBinding

    val sessionManager = AuthUtils.manager

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

        Log.e("Ziad MyAccount",sessionManager.getToken().toString())
        if (sessionManager.getToken().equals(null)){
            binding.signupBtn.isVisible=false
            binding.myWalletBtn.isVisible=false
            binding.editAccountBtn.isVisible=false
            binding.myOrdersBtn.isVisible=false
            binding.support2Btn.isVisible=false
            binding.logoutBtn.isVisible=false

            binding.Divider1.isVisible=false
            binding.Divider2.isVisible=false
            binding.Divider3.isVisible=false
            binding.Divider4.isVisible=false
            binding.Divider10.isVisible=false

            binding.loginBtn.setOnClickListener {

                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()

                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible = false
            }

            binding.language2Btn.setOnClickListener {
                Toast.makeText(requireContext(), "coming soon", Toast.LENGTH_SHORT).show()
            }
            binding.termsAndConditionsBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"))
                startActivity(intent)
            }
            binding.aboutBtn.setOnClickListener {
                val action = MyAccountFragmentDirections.actionMyAccountFragment2ToAboutFragment()
                findNavController().navigate(action)
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible = false
            }



        }else {

            binding.loginBtn.isVisible=false
            binding.signupBtn.isVisible = false

            binding.Divider1.isVisible=false
            binding.Divider10.isVisible=false

            binding.editAccountBtn.setOnClickListener {
                val action =
                    MyAccountFragmentDirections.actionMyAccountFragment2ToEditMyAccountFragment()
                findNavController().navigate(action)
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible =
                    false
            }
            binding.myWalletBtn.setOnClickListener {
                Toast.makeText(requireContext(), "coming soon", Toast.LENGTH_SHORT).show()

            }
            binding.myOrdersBtn.setOnClickListener {
                val action =
                    MyAccountFragmentDirections.actionMyAccountFragment2ToMyOrdersFragment()
                findNavController().navigate(action)
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible =
                    false
            }
            binding.support2Btn.setOnClickListener {
                Toast.makeText(requireContext(), "coming soon", Toast.LENGTH_SHORT).show()
            }

            binding.language2Btn.setOnClickListener {
                Toast.makeText(requireContext(), "coming soon", Toast.LENGTH_SHORT).show()
            }
            binding.termsAndConditionsBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"))
                startActivity(intent)
            }
            binding.aboutBtn.setOnClickListener {
                val action = MyAccountFragmentDirections.actionMyAccountFragment2ToAboutFragment()
                findNavController().navigate(action)
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible = false
            }

            binding.logoutBtn.setOnClickListener {
                val view = layoutInflater.inflate(R.layout.logout_dialog,null)
                val logoutBoxBuilder = AlertDialog.Builder(requireContext()).setView(view).create()
                logoutBoxBuilder.show()
                val cancelButton: Button = view.findViewById(R.id.btn_cancel)
                cancelButton.setOnClickListener {
                    logoutBoxBuilder.dismiss()
                }
                val okButton: Button = view.findViewById(R.id.btn_ok)
                okButton.setOnClickListener {
                    sessionManager.deleteData()

                    val intent = Intent(requireContext(),MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }

        }




        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible=true
    }

}