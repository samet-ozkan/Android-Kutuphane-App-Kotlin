package com.sametozkan.kutuphane.presentation.kutuphane.giris

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.databinding.ActivityKutuphaneGirisBinding
import com.sametozkan.kutuphane.presentation.dialog.ErrorDialog
import com.sametozkan.kutuphane.util.MyResult
import com.sametozkan.kutuphane.presentation.kutuphane.home.KutuphaneHomeActivity
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.LoadingManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KutuphaneGirisActivity : AppCompatActivity() {

    private lateinit var binding : ActivityKutuphaneGirisBinding
    val viewModel: KutuphaneGirisViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKutuphaneGirisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel

        setupGirisYapButton();
        observeLoading()
    }

    private fun observeLoading(){
        LoadingManager.loading.observe(this){
            binding.isLoading = it
        }
    }

    private fun setupGirisYapButton(){
        binding.girisYapButton.setOnClickListener {
            viewModel.login { result ->
                when (result) {
                    is MyResult.Success -> {
                        val jwtRes = result.data
                        viewModel.setSession(jwtRes)
                        startActivity(Intent(this, KutuphaneHomeActivity::class.java))
                    }
                    is MyResult.Error -> {
                        when(result.responseCode){
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