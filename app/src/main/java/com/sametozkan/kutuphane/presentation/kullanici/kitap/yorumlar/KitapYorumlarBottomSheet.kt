package com.sametozkan.kutuphane.presentation.kullanici.kitap.yorumlar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sametozkan.kutuphane.databinding.BottomSheetKitapYorumlarBinding
import com.sametozkan.kutuphane.presentation.kullanici.kitap.KitapViewModel
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.MyResult

class KitapYorumlarBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetKitapYorumlarBinding
    private lateinit var rvAdapter: KitapYorumlarRvAdapter

    val sharedViewModel: KitapViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetKitapYorumlarBinding.inflate(inflater, container, false)
        binding.sharedViewModel = sharedViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupGonderButton()

        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun setupGonderButton() {
        binding.gonderButton.setOnClickListener {
            sharedViewModel.yorumGonder { myResult ->
                when (myResult) {
                    is MyResult.Success -> {
                        Toast.makeText(context, "Yorum gönderildi", Toast.LENGTH_SHORT).show()
                        refreshComments()
                        binding.yorumEditText.setText("")
                    }

                    is MyResult.Error -> {
                        ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception, parentFragmentManager, context)
                    }
                }
            }
        }
    }

    private fun refreshComments() {
        sharedViewModel.fetchYorumlar { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    binding.isEmpty = myResult.data.isEmpty()
                    rvAdapter.list = myResult.data
                }

                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception, parentFragmentManager, context)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        refreshComments()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        super.onCreateDialog(savedInstanceState).apply {
            setCanceledOnTouchOutside(true)
        }

    private fun setupRv() {
        rvAdapter = KitapYorumlarRvAdapter(ArrayList())
        binding.yorumlarRv.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
}