package com.sametozkan.kutuphane.presentation.kullanici.kitap

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sametozkan.kutuphane.databinding.ActivityKitapBinding
import com.sametozkan.kutuphane.presentation.dialog.ConfirmationDialog
import com.sametozkan.kutuphane.presentation.dialog.SuccessDialog
import com.sametozkan.kutuphane.presentation.kullanici.kitap.yorumlar.KitapYorumlarBottomSheet
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KitapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKitapBinding
    val viewModel: KitapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKitapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        getIntentExtras()
        fetchData()
        setupOduncAlButton()
        setupYorumlarButton()
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

    private fun setupYorumlarButton() {
        binding.yorumlarButton.setOnClickListener {
            val kitapYorumlarBottomSheet = KitapYorumlarBottomSheet()
            kitapYorumlarBottomSheet.show(supportFragmentManager, "Yorumlar")
        }
    }

    private fun setupOduncAlButton(){
        binding.oduncAlButton.setOnClickListener {
            ConfirmationDialog("Kitabı ödünç almak istediğinize emin misiniz?"){
                confirmationDialog ->
                confirmationDialog.dismiss()
                viewModel.oduncAl { myResult ->
                    when(myResult){
                        is MyResult.Success -> {
                            SuccessDialog("Ödünç alma isteği gönderildi."){
                                successDialog ->
                                successDialog.dismiss()
                            }.show(supportFragmentManager, "Success")
                        }
                        is MyResult.Error -> {
                            ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception, supportFragmentManager, this)
                        }
                    }
                }
            }.show(supportFragmentManager, "Confirmation")
        }
    }

    private fun fetchData() {
        viewModel.fetchKutuphane { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    viewModel.kutuphane.value = myResult.data
                }

                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception, supportFragmentManager, this)
                }
            }
        }

        viewModel.fetchKitap { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    viewModel.kitap.value = myResult.data
                }

                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception, supportFragmentManager, this)
                }
            }
        }
    }

    private fun getIntentExtras() {
        if (viewModel.kutuphaneId == null || viewModel.kitapId == null) {
            val kutuphaneId = intent.getLongExtra("Kütüphane ID", 0)
            val kitapId = intent.getLongExtra("Kitap ID", 0)
            if (kutuphaneId != 0L && kitapId != 0L) {
                viewModel.kutuphaneId = kutuphaneId
                viewModel.kitapId = kitapId
            } else
                println("Intent ile kütüphane id veya kitap id alınamadı!")
        }
    }
}