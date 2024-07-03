package com.sametozkan.kutuphane.presentation.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.databinding.BottomSheetKitapBinding

class KitapBottomSheet(private val kitapRes: KitapRes) : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetKitapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetKitapBinding.inflate(inflater, container, false)
        binding.kitapRes = kitapRes
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        super.onCreateDialog(savedInstanceState).apply {
            setCanceledOnTouchOutside(true)
        }
}