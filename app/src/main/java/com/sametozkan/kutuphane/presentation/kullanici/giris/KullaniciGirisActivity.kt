package com.sametozkan.kutuphane.presentation.kullanici.giris

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.databinding.ActivityKullaniciGirisBinding
import com.sametozkan.kutuphane.presentation.dialog.ErrorDialog
import com.sametozkan.kutuphane.presentation.kullanici.home.KullaniciHomeActivity
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KullaniciGirisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKullaniciGirisBinding
    val viewModel: KullaniciGirisViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKullaniciGirisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel

        setupGirisYapButton()
        observeLoading()
    }

    private fun observeLoading() {
        LoadingManager.loading.observe(this) {
            binding.isLoading = it
        }
    }

    private fun setupGirisYapButton() {
        binding.girisYapButton.setOnClickListener {
            viewModel.login { myResult ->
                when (myResult) {
                    is MyResult.Success -> {
                        viewModel.setSession(myResult.data)
                        val intent = Intent(this, KullaniciHomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }

                    is MyResult.Error -> {
                        when(myResult.responseCode){
                            401 -> ErrorDialog(getString(R.string.wrong_email_or_password)).show(supportFragmentManager, "Hata")
                            404 -> ErrorDialog(getString(R.string.wrong_email_or_password)).show(supportFragmentManager, "Hata")
                            else -> ErrorDialog(getString(R.string.error_generic)).show(supportFragmentManager, "Hata")
                        }
                    }
                }
            }
        }
    }
}