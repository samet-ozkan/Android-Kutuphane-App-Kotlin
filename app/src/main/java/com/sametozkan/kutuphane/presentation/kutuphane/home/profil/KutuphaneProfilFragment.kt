package com.sametozkan.kutuphane.presentation.kutuphane.home.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sametozkan.kutuphane.MainActivity
import com.sametozkan.kutuphane.databinding.FragmentKutuphaneProfilBinding
import com.sametozkan.kutuphane.presentation.kutuphane.home.KutuphaneHomeViewModel
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KutuphaneProfilFragment : Fragment() {

    private lateinit var binding: FragmentKutuphaneProfilBinding
    val sharedViewModel: KutuphaneHomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKutuphaneProfilBinding.inflate(inflater, container, false)
        setupKutuphane()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupKutuphane() {
        sharedViewModel.fetchKutuphane { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    binding.kutuphaneRes = myResult.data
                }

                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception, parentFragmentManager, context)
                }
            }
        }
    }
}