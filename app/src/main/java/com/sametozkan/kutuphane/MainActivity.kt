package com.sametozkan.kutuphane

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sametozkan.kutuphane.databinding.ActivityMainBinding
import com.sametozkan.kutuphane.presentation.kutuphane.KutuphaneGirisActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupKutuphaneButton()
    }

    private fun setupKutuphaneButton(){
        binding.kutuphaneButton.setOnClickListener {
            val intent = Intent(this, KutuphaneGirisActivity::class.java)
            startActivity(intent)
        }
    }
}