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
import com.sametozkan.kutuphane.databinding.DialogErrorBinding
import com.sametozkan.kutuphane.databinding.DialogSuccessBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessDialog(private val successMessage: String, private val onDismissListener : (SuccessDialog) -> Unit ) : DialogFragment() {

    private lateinit var binding: DialogSuccessBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSuccessBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.message = successMessage
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
        setTamamButton()
    }

    private fun setTamamButton(){
        binding.tamamButton.setOnClickListener {
            onDismissListener(this)
        }
    }
}