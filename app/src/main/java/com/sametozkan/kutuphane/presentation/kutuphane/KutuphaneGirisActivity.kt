package com.sametozkan.kutuphane.presentation.kutuphane

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sametozkan.kutuphane.databinding.ActivityKutuphaneGirisBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KutuphaneGirisActivity : AppCompatActivity() {

    private lateinit var binding : ActivityKutuphaneGirisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKutuphaneGirisBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}