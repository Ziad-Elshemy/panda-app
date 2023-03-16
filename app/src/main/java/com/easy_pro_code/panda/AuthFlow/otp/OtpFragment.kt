package com.easy_pro_code.panda.AuthFlow.otp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.easy_pro_code.panda.HomeFlow.HomeActivity
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentOtpBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class OtpFragment : Fragment() {

    lateinit var binding:FragmentOtpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_otp,container,false)


        binding.pinViewOtpCode.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().length == 6)
                {


                    runBlocking {
                        val intent = Intent(requireContext(), HomeActivity::class.java)
                        startActivity(intent)
                        delay(3000)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        return binding.root
    }

}