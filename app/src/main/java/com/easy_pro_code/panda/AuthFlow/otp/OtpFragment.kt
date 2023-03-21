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
import com.easy_pro_code.panda.data.Models.remote_firebase.AuthUtils
import com.easy_pro_code.panda.data.Models.remote_firebase.FirebaseUtils
import com.easy_pro_code.panda.databinding.FragmentOtpBinding
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit


class OtpFragment : AuthFragment() {

    lateinit var binding:FragmentOtpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_otp,container,false)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                AuthUtils.manager.deleteData()
                findNavController().popBackStack()
            }
        })

        val args: OtpFragmentArgs by navArgs()
        val verficationId: String = args.verficationId
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

    private fun verifyPhoneNumber(verificationId: String, code: String)
    {
        //val code = codeVerification()
        Log.i("Error", code)
        if(code.length == 6 && TextUtils.isDigitsOnly(code))
        {
            loadingState()

            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            FirebaseUtils.auth.signInWithCredential(credential).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    runBlocking {
                        delay(3000)
                        signedInSuccessful()

                    }
                    //successStart()
                }else{
                    //EditHere
                    errorState()
                }

            }
        }
        else{
            Log.i("Error", "Error code")
        }
    }
    private fun signedInSuccessful() {
        binding.progressBarLoadingOtpVerification.visibility = View.GONE
        val intent=Intent(requireContext(),HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
    override fun successState(verificationId: String, token: PhoneAuthProvider.ForceResendingToken)
    {
        Toast.makeText(requireContext(), "Welcome", Toast.LENGTH_LONG).show()
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