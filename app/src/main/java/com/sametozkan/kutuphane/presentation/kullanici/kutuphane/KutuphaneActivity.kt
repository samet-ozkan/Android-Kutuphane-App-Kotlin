package com.sametozkan.kutuphane.presentation.kullanici.kutuphane

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sametozkan.kutuphane.databinding.ActivityKutuphaneBinding
import com.sametozkan.kutuphane.presentation.kullanici.kutuphane.kitaplar.KitaplarActivity
import com.sametozkan.kutuphane.presentation.kullanici.kutuphane.yorumlar.KutuphaneYorumlarBottomSheet
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KutuphaneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKutuphaneBinding
    val viewModel: KutuphaneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKutuphaneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupKutuphaneId()
        setupYorumlarButton()
        setupKitaplarButton()
        observeLoading()
        setupBackButton()
    }

    private fun setupBackButton(){
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeLoading(){
        LoadingManager.loading.observe(this){
            binding.isLoading = it
        }
    }

    private fun setupKitaplarButton() {
        binding.kitaplarButton.setOnClickListener {
            val intent = Intent(this, KitaplarActivity::class.java)
            intent.putExtra("Kütüphane ID", viewModel.kutuphaneId)
            startActivity(intent)
        }
    }

    private fun setupYorumlarButton() {
        binding.yorumlarButton.setOnClickListener {
            val kutuphaneYorumlarBottomSheet = KutuphaneYorumlarBottomSheet()
            kutuphaneYorumlarBottomSheet.show(supportFragmentManager, "Yorumlar")
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchKutuphane { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    binding.kutuphane = myResult.data
                }

                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception.message, supportFragmentManager, this)
                }
            }
        }
    }

    private fun setupKutuphaneId() {
        if (viewModel.kutuphaneId == null) {
            val kutuphaneId = intent.getLongExtra("Kütüphane ID", 0)
            if (kutuphaneId != 0L)
                viewModel.kutuphaneId = kutuphaneId
            else
                println("Intent ile kütüphane id alınamadı!")
        }
    }
}