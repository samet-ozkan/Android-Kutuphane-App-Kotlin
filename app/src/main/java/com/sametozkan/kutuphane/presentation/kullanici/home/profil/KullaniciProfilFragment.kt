package com.sametozkan.kutuphane.presentation.kullanici.home.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sametozkan.kutuphane.MainActivity
import com.sametozkan.kutuphane.databinding.FragmentKullaniciProfilBinding
import com.sametozkan.kutuphane.databinding.FragmentKutuphaneProfilBinding
import com.sametozkan.kutuphane.presentation.kullanici.home.KullaniciHomeViewModel
import com.sametozkan.kutuphane.presentation.kutuphane.home.KutuphaneHomeViewModel
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KullaniciProfilFragment : Fragment() {

    private lateinit var binding: FragmentKullaniciProfilBinding
    val sharedViewModel: KullaniciHomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKullaniciProfilBinding.inflate(inflater, container, false)
        setupKullanici()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCikisYapButton()
    }

    private fun setupCikisYapButton(){
        binding.cikisYapButton.setOnClickListener {
            sharedViewModel.logout()
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    private fun setupKullanici() {
        sharedViewModel.fetchKullanici { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    binding.kullaniciRes = myResult.data
                }
                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception.message, parentFragmentManager, context)
                }
            }
        }
    }
}