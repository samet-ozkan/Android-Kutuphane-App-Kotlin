package com.sametozkan.kutuphane.presentation.kullanici.giris

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.databinding.ActivityKullaniciGirisBinding
import com.sametozkan.kutuphane.presentation.dialog.ErrorDialog
import com.sametozkan.kutuphane.presentation.kullanici.home.KullaniciHomeActivity
import com.sametozkan.kutuphane.presentation.kullanici.kaydol.KullaniciKaydolActivity
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

        setupHemenKaydolun()
        setupGirisYapButton()
        observeLoading()
    }

    private fun setupHemenKaydolun(){
        binding.hemenKaydolun.setOnClickListener {
            val intent = Intent(this, KullaniciKaydolActivity::class.java)
            startActivity(intent)
        }
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
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }

                    is MyResult.Error -> {
                        ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception, supportFragmentManager, this)
                        }
                    }
                }
            }
    }
}