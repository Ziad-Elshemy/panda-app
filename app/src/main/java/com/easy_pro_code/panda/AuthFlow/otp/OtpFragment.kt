package com.easy_pro_code.panda.AuthFlow.otp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.easy_pro_code.panda.AuthFragment.AuthFragment
import com.easy_pro_code.panda.HomeFlow.HomeActivity
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.data.Models.remote_backend.UserData
import com.easy_pro_code.panda.databinding.FragmentOtpBinding
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.data.Models.remote_firebase.FirebaseUtils
import com.easy_pro_code.panda.data.Models.remote_firebase.PhoneVerification

import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit


class OtpFragment : AuthFragment() {

    lateinit var binding:FragmentOtpBinding
    private lateinit var userData: UserData
    private lateinit var phoneData: PhoneVerification
    lateinit var mVerificationId: String
    val otpFragmentArgs:OtpFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_otp,container,false)

        userData=otpFragmentArgs.userData
        phoneData=otpFragmentArgs.phoneData
        mVerificationId=phoneData.verificationId

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                AuthUtils.manager.deleteData()
                findNavController().popBackStack()
            }
        })

        val args: OtpFragmentArgs by navArgs()
        val verficationId: String = args.phoneData.verificationId
        Log.i("michael", verficationId)


        binding.pinViewOtpCode.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length == 6) {
                    verifyPhoneNumber(verficationId, binding.pinViewOtpCode.text.toString())

                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        return binding.root
    }

    private fun verifyPhoneNumber(verificationId: String, code: String) {
        if (code.length == 6 && TextUtils.isDigitsOnly(code)) {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            Firebase.auth.signInWithCredential(credential).addOnCompleteListener {
                if (it.isSuccessful) {
                    AuthUtils.manager.saveAuthToken(userData,phoneData.phoneNumber)
                    Log.i("Phone", "TokenSaved")
                    val homeIntent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(homeIntent)
                    requireActivity().finish()
                } else {
                    Toast.makeText(requireContext(), "Enter Valid Code", Toast.LENGTH_LONG).show()
                }

            }
        } else {
            Log.i("Error", "Error code")
        }
    }

    private fun signedInSuccessful() {
        binding.progressBarLoadingOtpVerification.visibility = View.GONE
        val intent=Intent(requireContext(),HomeActivity::class.java)
        startActivity(intent)
    }
    override fun successState(verificationId: String, token: PhoneAuthProvider.ForceResendingToken)
    {
        Toast.makeText(requireContext(), "Welcome", Toast.LENGTH_LONG).show()
        /// by navArgs

//        AuthUtils.manager.saveAuthToken(user,phoneNumber)

    }

    override fun errorState() {
        binding.progressBarLoadingOtpVerification.visibility = View.GONE
        Toast.makeText(requireContext(), "Enter Valid Code", Toast.LENGTH_LONG).show()
    }

    override fun loadingState() {
        val keyboard =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(binding.root.windowToken, 0)
        binding.progressBarLoadingOtpVerification.visibility = View.VISIBLE
    }

    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(FirebaseUtils.auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

}