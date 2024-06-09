package com.sametozkan.kutuphane.presentation.kutuphane.home

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sametozkan.kutuphane.MainActivity
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.databinding.ActivityKutuphaneHomeBinding
import com.sametozkan.kutuphane.presentation.kutuphane.home.kitapistekleri.KutuphaneKitapIstekleriFragment
import com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.KutuphaneKitapYonetimiFragment
import com.sametozkan.kutuphane.presentation.kutuphane.home.profil.KutuphaneProfilFragment
import com.sametozkan.kutuphane.util.KullaniciFragments
import com.sametozkan.kutuphane.util.MyResult
import com.sametozkan.kutuphane.util.KutuphaneFragments
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KutuphaneHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKutuphaneHomeBinding
    val viewModel: KutuphaneHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKutuphaneHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel

        setChangeFragmentObserver()
        setNavigationDrawer()
        setNavigationHeader()
        setNavigationItemClickListener()

        initFrameLayout()
    }

    private fun initFrameLayout() {
        viewModel.changeFragment.value = KutuphaneFragments.KUTUPHANE_PROFIL
    }

    private fun setNavigationHeader() {
        viewModel.fetchKutuphane { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    val kutuphane = myResult.data
                    binding.navigationView.getHeaderView(0).apply {
                        findViewById<TextView>(R.id.fullName).text = kutuphane.adi
                        findViewById<TextView>(R.id.email).text = kutuphane.account.email
                    }
                }

                is MyResult.Error -> {
                    myResult.exception.printStackTrace()
                }
            }
        }
    }

    private fun setNavigationDrawer() {
        binding.toolbar.setNavigationIcon(R.drawable.baseline_dehaze_24)
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
    }

    private fun setNavigationItemClickListener() {
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuProfil -> {
                    binding.drawerLayout.close()
                    viewModel.changeFragment.value =
                        KutuphaneFragments.KUTUPHANE_PROFIL
                }

                R.id.menuKitapYonetimi -> {
                    binding.drawerLayout.close()
                    viewModel.changeFragment.value =
                        KutuphaneFragments.KITAP_YONETIMI
                }

                R.id.menuKitapIstekleri -> {
                    binding.drawerLayout.close()
                    viewModel.changeFragment.value = KutuphaneFragments.KITAP_ISTEKLERI
                }

                R.id.logout -> {
                    SessionManager(applicationContext).clear()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                else -> false
            }
            true
        }
    }

    private fun setChangeFragmentObserver() {
        viewModel.changeFragment.observe(this, Observer { value ->

            when (value) {
                KutuphaneFragments.KUTUPHANE_PROFIL -> replaceFragment(
                    KutuphaneProfilFragment(),
                    "Profil"
                )

                KutuphaneFragments.KITAP_YONETIMI -> replaceFragment(
                    KutuphaneKitapYonetimiFragment(),
                    "Kitap Yönetimi"
                )

                KutuphaneFragments.KITAP_ISTEKLERI -> replaceFragment(
                    KutuphaneKitapIstekleriFragment(),
                    "Kitap İstekleri"
                )
            }

        })
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, fragment).commit()
        binding.toolbarTitle.text = title
    }
}