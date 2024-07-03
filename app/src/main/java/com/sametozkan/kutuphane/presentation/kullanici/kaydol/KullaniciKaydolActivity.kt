package com.sametozkan.kutuphane.presentation.kullanici.kaydol

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.databinding.ActivityKullaniciKaydolBinding
import com.sametozkan.kutuphane.presentation.dialog.SuccessDialog
import com.sametozkan.kutuphane.presentation.kullanici.giris.KullaniciGirisActivity
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KullaniciKaydolActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKullaniciKaydolBinding

    val viewModel: KullaniciKaydolViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKullaniciKaydolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupKaydolButton()
        setupBackButton()
        observeLoading()
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun startLoginActivity() {
        val intent = Intent(this, KullaniciGirisActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupKaydolButton() {
        binding.kaydolButton.setOnClickListener {
            viewModel.kaydol { myResult ->
                when (myResult) {
                    is MyResult.Success -> {
                        val successDialog =
                            SuccessDialog(getString(R.string.registration_success_message)) { successDialog ->
                                successDialog.dismiss()
                                startLoginActivity()
                            }
                        successDialog.show(supportFragmentManager, "Success")
                    }

                    is MyResult.Error -> {
                        ErrorUtil.showErrorDialog(
                            myResult.responseCode,
                            myResult.exception,
                            supportFragmentManager,
                            this
                        )
                    }
                }
            }
        }
    }

    private fun observeLoading() {
        LoadingManager.loading.observe(this) {
            binding.isLoading = it
        }
    }

}