package com.sametozkan.kutuphane.presentation.kutuphane.giris

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sametozkan.kutuphane.databinding.ActivityKutuphaneGirisBinding
import com.sametozkan.kutuphane.domain.usecase.MyResult
import com.sametozkan.kutuphane.presentation.kutuphane.home.KutuphaneHomeActivity
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
                        val exception = result.exception
                        println("Exception: " + exception)
                    }
                }
            }
        }
    }
}