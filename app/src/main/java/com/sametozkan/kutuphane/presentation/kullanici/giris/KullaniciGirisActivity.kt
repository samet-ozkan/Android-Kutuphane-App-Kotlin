package com.sametozkan.kutuphane.presentation.kullanici.giris

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sametozkan.kutuphane.databinding.ActivityKullaniciGirisBinding
import com.sametozkan.kutuphane.presentation.kullanici.home.KullaniciHomeActivity
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KullaniciGirisActivity : AppCompatActivity() {

    private lateinit var binding : ActivityKullaniciGirisBinding
    val viewModel : KullaniciGirisViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKullaniciGirisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel

        setupGirisYapButton()
    }

    private fun setupGirisYapButton(){
        binding.girisYapButton.setOnClickListener {
            println(viewModel.email.value?.toString() + " Şifre: " + viewModel.password.value?.toString())
            viewModel.login { myResult ->
            when(myResult){
                is MyResult.Success -> {
                    val intent = Intent(this, KullaniciHomeActivity::class.java)
                    startActivity(intent)
                    viewModel.setSession(myResult.data)
                    Toast.makeText(this, "Giriş başarılı!", Toast.LENGTH_SHORT).show()
                }
                is MyResult.Error -> {
                    Toast.makeText(this, "Giriş başarısız!", Toast.LENGTH_SHORT).show()
                    myResult.exception.printStackTrace()
                }
            }
            }
        }
    }
}