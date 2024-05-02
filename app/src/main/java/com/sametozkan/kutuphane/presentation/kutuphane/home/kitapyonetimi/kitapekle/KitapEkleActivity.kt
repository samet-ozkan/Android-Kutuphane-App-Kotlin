package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sametozkan.kutuphane.databinding.ActivityKitapEkleBinding
import com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle.kitapolustur.KitapOlusturFragment
import com.sametozkan.kutuphane.util.FragmentNameConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KitapEkleActivity : AppCompatActivity() {

    val viewModel: KitapEkleViewModel by viewModels()
    lateinit var binding: ActivityKitapEkleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKitapEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeChangeFragment()
        viewModel.changeFragment.value = FragmentNameConstants.KITAP_EKLE
    }

    private fun observeChangeFragment() {
        viewModel.changeFragment.observe(this, Observer { fragment ->
            when (fragment) {
                FragmentNameConstants.KITAP_EKLE -> replaceFragment(
                    KitapEkleFragment(),
                    "Kitap Ekle"
                )

                FragmentNameConstants.KITAP_OLUSTUR -> replaceFragment(
                    KitapOlusturFragment(),
                    "Kitap Oluştur"
                )
            }
        })
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, fragment).commit()
        binding.toolbarTitle.text = title
    }
}