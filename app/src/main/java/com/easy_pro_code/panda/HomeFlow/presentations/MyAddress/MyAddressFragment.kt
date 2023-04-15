package com.easy_pro_code.panda.HomeFlow.presentations.MyAddress

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.easy_pro_code.panda.R
import com.easy_pro_code.panda.databinding.FragmentMyAddressBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class MyAddressFragment : Fragment() {

    private lateinit var binding: FragmentMyAddressBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var adapter: MyAddressAdapter
    val hintFlag:Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_address, container, false)
        adapter = MyAddressAdapter()
        binding.addressRv.adapter = adapter



        bottomSheetDialog = BottomSheetDialog(requireContext())
        createDialog()

        binding.addAddressBtn.setOnClickListener {
            bottomSheetDialog.show()
        }
        /// back from this fragment

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })

        return binding.root
    }

    @SuppressLint("MissingInflatedId")
    fun createDialog()
    {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_add_address_item, null, false)
        val addButton = view.findViewById<Button>(R.id.btn_addAddress)

        val address = view.findViewById<EditText>(R.id.et_name)
        val phone = view.findViewById<EditText>(R.id.et_phone)



        addButton.setOnClickListener {

            if(adapter!=null)
                binding.hintTv.isVisible= false

binding.addressRv
            if (address==null || address.text.toString() == "")
            {
                address.error = "this field is required"

            }
            else if( phone.text.toString() == ""  || !TextUtils.isDigitsOnly(phone.text) || phone.text.length != 11)
            {
                phone.error= "Enter valid number"


            }
            else
            {
                adapter.add(AddressItem(address.text.toString(), phone.text.toString()))
                address.text.clear()
                phone.text.clear()
                bottomSheetDialog.dismiss()
            }

        }
        bottomSheetDialog.setContentView(view)
    }


}
