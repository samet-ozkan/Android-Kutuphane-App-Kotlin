package com.sametozkan.kutuphane.presentation.kullanici.home.kitaponerileri

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sametozkan.kutuphane.databinding.FragmentKitapOnerileriBinding
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KitapOnerileriFragment : Fragment() {

    private lateinit var binding: FragmentKitapOnerileriBinding

    val viewModel: KitapOnerileriViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKitapOnerileriBinding.inflate(inflater, container, false)
        binding.kitapOnerileri = ArrayList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGuncelleButton()
        guncelle()
    }

    private fun setupGuncelleButton() {
        binding.onerileriGuncelleButton.setOnClickListener {
            guncelle()
        }
    }

    private fun guncelle() {
        viewModel.fetchKitapOnerileri { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    binding.kitapOnerileri = myResult.data
                }

                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception.message, parentFragmentManager, context)
                }
            }
        }
    }
}