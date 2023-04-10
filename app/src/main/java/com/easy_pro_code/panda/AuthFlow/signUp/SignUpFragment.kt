package com.easy_pro_code.panda.AuthFlow.signUp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.AuthFlow.Model.RegistrationViewModel
import com.easy_pro_code.panda.HomeFlow.HomeActivity
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentSignUpBinding
import com.easy_pro_code.panda.data.Models.remote_firebase.FirebaseUtils
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    lateinit var userName: EditText
    lateinit var phoneNumber: EditText
    lateinit var email: EditText
    lateinit var signUpBtn: Button
    lateinit var viewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)

        binding.skip.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)

        }
        binding.haveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        binding.signInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        initViews()
        subscribeLiveData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun sendPhoneNumber(callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks) {
        val options = PhoneAuthOptions.newBuilder(FirebaseUtils.auth)
            .setPhoneNumber("+2" + binding.etPhoneNumber.text.toString())       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private fun subscribeLiveData() {
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            it?.let { signUpResponse ->
                if (signUpResponse.message.equals("Failed! Phone is already in use!")) {
                    Toast.makeText(
                        requireContext(),
                        "Failed! Phone is already in use!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (signUpResponse.message.equals("something went wrong")) {
                    Toast.makeText(requireContext(), "something went wrong", Toast.LENGTH_SHORT)
                        .show()
                } else if (signUpResponse.message.equals("Connection failed,please try again")) {
                    Toast.makeText(
                        requireContext(),
                        "Connection failed,please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun initViews() {
        var firstName: String = ""
        var lastName: String = ""
        userName = binding.userName
        phoneNumber = binding.etPhoneNumber
        email = binding.email
        signUpBtn = binding.btnSignUp
        binding.signInBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        signUpBtn.setOnClickListener {
            val phoneNumber = binding.etPhoneNumber.text
            val email = binding.email.text

            if (phoneNumber.isBlank() || phoneNumber.isEmpty() || !TextUtils.isDigitsOnly(
                    phoneNumber
                ) || phoneNumber.length != 11
            ) {
                Toast.makeText(requireContext(), "Enter Valid Number", Toast.LENGTH_LONG).show()

            } else if (email.isEmpty() || (!Pattern.matches(
                    "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",
                    email
                )
                        )
            ) {
                Toast.makeText(requireContext(), "Enter Valid Email", Toast.LENGTH_LONG).show()

            } else {
                verify()
            }
        }

        binding.firstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                firstName = binding.firstName.text.toString()
                binding.userName.setText("$firstName $lastName")
            }

            override fun afterTextChanged(s: Editable?) {
                binding.userName.isEnabled = false
            }
        })
        binding.lastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lastName = binding.lastName.text.toString()
                binding.userName.setText(firstName + "_" + lastName)
            }

            override fun afterTextChanged(s: Editable?) {
                binding.userName.isEnabled = false
            }
        })

    }

    private fun loadingState() {
        binding.btnSignUp.visibility = View.GONE
        binding.progressIndicator.visibility = View.VISIBLE

    }

    private fun verify(){
        val userNameFirst = userName.text.toString().trim()
        val UserphoneNumber = phoneNumber.text.toString().trim()
        val userEmail = email.text.toString().trim()
        val firstName=binding.firstName.text.toString().trim()
        val lastName=binding.lastName.text.toString().trim()
        val emailText=binding.email.text.toString().trim()

        if (userNameFirst.isEmpty()) {
            userName.error = "Please Enter Your User Name"
            return
        } else if (UserphoneNumber.isEmpty()) {
            phoneNumber.error = "Please Enter Your Phone"
        } else if (userEmail.isEmpty() || (!Pattern.matches(
                "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",
                userEmail
            )
                    )) {
            email.error = "Please Enter Vaild Email"
            return
        } else {
            viewModel.signUp(
                userNameFirst,
                firstName,
                lastName,
                binding.etPhoneNumber.text.toString(),
                emailText
            )

        }


    }


}