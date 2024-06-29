package com.sametozkan.kutuphane.presentation.kutuphane.kaydol

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.databinding.ActivityKutuphaneKaydolBinding
import com.sametozkan.kutuphane.presentation.dialog.SuccessDialog
import com.sametozkan.kutuphane.presentation.kutuphane.giris.KutuphaneGirisActivity
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KutuphaneKaydolActivity : AppCompatActivity() {

    private lateinit var binding : ActivityKutuphaneKaydolBinding

    val viewModel : KutuphaneKaydolViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKutuphaneKaydolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupSehirler()
        setupKaydolButton()
        setupBackButton()
        observeLoading()
    }

    private fun setupSehirler(){
        val sehirler = resources.getStringArray(R.array.turkey_cities)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, sehirler)
        binding.sehir.setAdapter(adapter)
        binding.sehir.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val selectedCity = parent.getItemAtPosition(position).toString()
            viewModel.sehir.value = selectedCity
        }
    }

    private fun setupBackButton(){
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun startLoginActivity(){
        val intent = Intent(this, KutuphaneGirisActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupKaydolButton(){
        binding.kaydolButton.setOnClickListener {
            viewModel.kaydol {
                    myResult ->
                when(myResult){
                    is MyResult.Success -> {
                        val successDialog = SuccessDialog(getString(R.string.registration_success_message)) {
                            startLoginActivity()
                        }
                        successDialog.show(supportFragmentManager, "Success")
                    }
                    is MyResult.Error -> {
                        ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception, supportFragmentManager, this)
                    }
                }
            }
        }
    }

    private fun observeLoading(){
        LoadingManager.loading.observe(this){
            binding.isLoading = it
        }
    }

}