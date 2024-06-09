package com.sametozkan.kutuphane.presentation.kullanici.home.kitap

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sametozkan.kutuphane.databinding.ActivityKitapBinding
import com.sametozkan.kutuphane.databinding.ActivityKitaplarBinding
import com.sametozkan.kutuphane.presentation.kullanici.home.kitap.yorumlar.KitapYorumlarBottomSheet
import com.sametozkan.kutuphane.presentation.kullanici.home.kutuphane.yorumlar.KutuphaneYorumlarBottomSheet
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
    }

    private fun setupYorumlarButton() {
        binding.yorumlarButton.setOnClickListener {
            val kutuphaneYorumlarBottomSheet = KitapYorumlarBottomSheet()
            kutuphaneYorumlarBottomSheet.show(supportFragmentManager, "Yorumlar")
        }
    }

    private fun setupOduncAlButton(){
        binding.oduncAlButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Kitabı ödünç almak istediğinize emin misiniz?")
                .setPositiveButton("Evet") { dialog, id ->
                    viewModel.oduncAl { myResult ->
                        when(myResult){
                            is MyResult.Success -> {
                                Toast.makeText(this, "Ödünç alma isteği gönderildi.", Toast.LENGTH_LONG).show()
                            }
                            is MyResult.Error -> {
                                Toast.makeText(this, "Ödünç alma isteği gönderilirken hata oluştu.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
                .setNegativeButton("Hayır") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }

    private fun fetchData() {
        viewModel.fetchKutuphane { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    viewModel.kutuphane.value = myResult.data
                }

                is MyResult.Error -> {
                    println(myResult.exception.message)
                    Toast.makeText(this, "Kutuphane verileri alınamadı!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.fetchKitap { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    viewModel.kitap.value = myResult.data
                }

                is MyResult.Error -> {
                    Toast.makeText(this, "Kitap verileri alınamadı!", Toast.LENGTH_SHORT).show()
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