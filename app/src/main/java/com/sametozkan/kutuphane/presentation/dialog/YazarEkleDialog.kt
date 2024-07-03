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
import com.sametozkan.kutuphane.databinding.DialogTurEkleBinding
import com.sametozkan.kutuphane.databinding.DialogYazarEkleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YazarEkleDialog(private val onEkleClickListener : (String, String) -> Unit) : DialogFragment() {

    private lateinit var binding: DialogYazarEkleBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogYazarEkleBinding.inflate(inflater, container, false)
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
            onEkleClickListener(binding.yazarAdi.text.toString(), binding.yazarSoyadi.text.toString())
            dismiss()
        }
    }
}