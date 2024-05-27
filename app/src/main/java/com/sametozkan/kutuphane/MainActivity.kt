package com.sametozkan.kutuphane

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sametozkan.kutuphane.data.datasource.local.sharedpreferences.SessionManager
import com.sametozkan.kutuphane.databinding.ActivityMainBinding
import com.sametozkan.kutuphane.presentation.kullanici.giris.KullaniciGirisActivity
import com.sametozkan.kutuphane.presentation.kullanici.home.KullaniciHomeActivity
import com.sametozkan.kutuphane.presentation.kutuphane.giris.KutuphaneGirisActivity
import com.sametozkan.kutuphane.presentation.kutuphane.home.KutuphaneHomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupKutuphaneButton()
        setupKullaniciButton()
        loginAutomatically()
    }

    private fun loginAutomatically() {
        val sessionManager = SessionManager(applicationContext)
        sessionManager.apply {
            getAccountID()?.let {
                getAccountType()?.let {
                    when (it) {
                        "kutuphane" -> {
                            val intent =
                                Intent(this@MainActivity, KutuphaneHomeActivity::class.java)
                            startActivity(intent)
                        }

                        "kullanici" -> {
                            val intent =
                                Intent(this@MainActivity, KullaniciHomeActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun setupKutuphaneButton() {
        binding.kutuphaneButton.setOnClickListener {
            val intent = Intent(this, KutuphaneGirisActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupKullaniciButton() {
        binding.kullaniciButton.setOnClickListener {
            val intent = Intent(this, KullaniciGirisActivity::class.java)
            startActivity(intent)
        }
    }
}