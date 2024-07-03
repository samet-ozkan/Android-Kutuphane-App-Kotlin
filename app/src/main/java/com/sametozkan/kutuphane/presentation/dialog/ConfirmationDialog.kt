package com.sametozkan.kutuphane.presentation.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.sametozkan.kutuphane.databinding.DialogConfirmationBinding

class ConfirmationDialog(private val message: String, private val onEvetClickListener : (ConfirmationDialog) -> Unit) : DialogFragment() {

    lateinit var binding : DialogConfirmationBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogConfirmationBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.message = message
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvetButton()
        setupIptalButton()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            val widthInDp = 300 // Width in dp

            val displayMetrics = resources.displayMetrics
            val widthInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthInDp.toFloat(), displayMetrics).toInt()

            window.setLayout(widthInPx, WindowManager.LayoutParams.WRAP_CONTENT)
        }
    }

    private fun setupEvetButton(){
        binding.evetButton.setOnClickListener {
            onEvetClickListener(this)
        }
    }

    private fun setupIptalButton(){
        binding.iptalButton.setOnClickListener {
            dismiss()
        }
    }
}