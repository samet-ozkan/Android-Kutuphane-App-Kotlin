package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle.kitapolustur

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.sametozkan.kutuphane.databinding.FragmentKitapOlusturBinding
import com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle.KitapEkleViewModel
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KitapOlusturFragment : Fragment() {

    lateinit var binding: FragmentKitapOlusturBinding
    val viewModel: KitapOlusturViewModel by viewModels()
    val sharedViewModel: KitapEkleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKitapOlusturBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupIsbn()
        setupStok()
        setupGoogleBooksApiButton()
        setupTemizleButton()
        setupOlusturButton()
    }

    private fun setupOlusturButton() {
        binding.olusturButton.setOnClickListener {
            println("Olustur butonuna basildi!")
            println("Kitap adı:" + viewModel.kitapAdi.value)
            viewModel.olustur { result ->
                when (result) {
                    is MyResult.Success -> {
                        Toast.makeText(context, "Başarılı!", Toast.LENGTH_SHORT).show()
                        requireActivity().finish()
                    }

                    is MyResult.Error -> {
                        Toast.makeText(context, "Başarısız!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupTemizleButton() {
        binding.temizleButton.setOnClickListener {
            viewModel.temizle()
        }
    }

    private fun setupIsbn() {
        sharedViewModel.isbn.value?.let {
            viewModel.isbn.value = it
        }
    }

    private fun setupStok() {
        sharedViewModel.stok.value?.let {
            viewModel.stok = it
        }
    }

    private fun setupGoogleBooksApiButton() {
        binding.googleBooksButton.setOnClickListener {
            viewModel.fetchKitapByIsbn { result ->
                when (result) {
                    is MyResult.Success -> {
                        val kitapReq = result.data
                        binding.apply {
                            kitapReq.isbn?.let {
                                isbnEditText.setText(kitapReq.isbn.toString())
                            }
                            kitapReq.adi?.let {
                                kitapAdiEditText.setText(kitapReq.adi)
                            }
                            kitapReq.yayinTarihi?.let {
                                yayinTarihiEditText.setText(kitapReq.yayinTarihi)
                            }
                            kitapReq.dil?.let {
                                dilEditText.setText(kitapReq.dil)
                            }
                            kitapReq.sayfaSayisi?.let {
                                sayfaSayisiEditText.setText(kitapReq.sayfaSayisi.toString())
                            }
                            kitapReq.aciklama?.let {
                                aciklamaEditText.setText(kitapReq.aciklama)
                            }
                        }
                        Toast.makeText(
                            context,
                            "Bilgiler Google Books API'den getirildi.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is MyResult.Error -> {
                        Toast.makeText(context, "Hata oluştu.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}