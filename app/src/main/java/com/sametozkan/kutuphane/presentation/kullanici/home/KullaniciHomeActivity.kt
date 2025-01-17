package com.sametozkan.kutuphane.presentation.kullanici.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sametozkan.kutuphane.MainActivity
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.databinding.ActivityKullaniciHomeBinding
import com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri.KitapIstekleriFragment
import com.sametozkan.kutuphane.presentation.kullanici.home.kutuphaneler.KutuphanelerFragment
import com.sametozkan.kutuphane.presentation.kullanici.home.profil.KullaniciProfilFragment
import com.sametozkan.kutuphane.util.KullaniciFragments
import com.sametozkan.kutuphane.util.LoadingManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KullaniciHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKullaniciHomeBinding
    val viewModel: KullaniciHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKullaniciHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setChangeFragmentObserver()
        setupBottomNavigationView()
        setupLogoutButton()

        initFrameLayout()
        observeLoading()
    }

    private fun observeLoading(){
        LoadingManager.loading.observe(this){
            binding.isLoading = it
        }
    }

    private fun initFrameLayout(){
        viewModel.changeFragment.value = KullaniciFragments.KUTUPHANELER;
    }

    private fun setupLogoutButton() {
        binding.logout.setOnClickListener {
            SessionManager(applicationContext).clear()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, fragment).commit()
        binding.toolbarTitle.text = title
    }

    private fun setChangeFragmentObserver() {
        viewModel.changeFragment.observe(this, Observer { value ->
            when (value) {
                KullaniciFragments.KUTUPHANELER -> {
                    replaceFragment(KutuphanelerFragment(), "Kütüphaneler")
                }
                KullaniciFragments.KITAP_ISTEKLERI -> {
                    replaceFragment(KitapIstekleriFragment(), "Kitap İstekleri")
                }
                KullaniciFragments.PROFIL -> {
                    replaceFragment(KullaniciProfilFragment(), "Profil")
                }
            }
        })
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuKutuphaneler -> {
                    viewModel.changeFragment.value = KullaniciFragments.KUTUPHANELER
                }
                R.id.menuKitapIsteklerim -> {
                    viewModel.changeFragment.value = KullaniciFragments.KITAP_ISTEKLERI
                }
                R.id.menuProfil -> {
                    viewModel.changeFragment.value = KullaniciFragments.PROFIL
                }
                else -> false
            }
            true
        }
    }

}