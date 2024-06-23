package com.sametozkan.kutuphane.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sametozkan.kutuphane.databinding.DialogErrorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ErrorDialog(private val errorMessage: String) : DialogFragment() {

    private lateinit var binding: DialogErrorBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogErrorBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.message = errorMessage
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTamamButton()
    }

    private fun setTamamButton(){
        binding.tamamButton.setOnClickListener {
            dismiss()
        }
    }
}