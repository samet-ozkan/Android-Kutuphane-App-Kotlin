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
import com.sametozkan.kutuphane.databinding.DialogSuccessBinding
import com.sametozkan.kutuphane.databinding.DialogTurEkleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TurEkleDialog(private val onEkleClickListener : (String) -> Unit) : DialogFragment() {

    private lateinit var binding: DialogTurEkleBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTurEkleBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEkleButton()
        setupVazgecButton()
    }

    private fun setupVazgecButton(){
        binding.vazgec.setOnClickListener {
            dismiss()
        }
    }

    private fun setupEkleButton(){
        binding.ekle.setOnClickListener {
            onEkleClickListener(binding.turAdi.text.toString())
            dismiss()
        }
    }
}