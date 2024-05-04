package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle.kitapolustur

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.data.dto.request.TurReq
import com.sametozkan.kutuphane.data.dto.request.YazarReq
import com.sametozkan.kutuphane.databinding.FragmentKitapOlusturBinding
import com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle.KitapEkleViewModel
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KitapOlusturFragment : Fragment() {

    lateinit var binding: FragmentKitapOlusturBinding
    private lateinit var yazarlarRvAdapter: YazarlarRvAdapter
    private lateinit var turlerRvAdapter: TurlerRvAdapter
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
        setupOlusturButton()
        setupYazarEkleButton()
        setupTurEkleButton()
        setupYazarlarRv()
        setupTurlerRv()
    }

    private fun setupTurlerRv() {
        turlerRvAdapter = TurlerRvAdapter(ArrayList()) { turReq ->
            viewModel.turler.value?.let { turReqs ->
                turReqs.remove(turReq)
                viewModel.turler.value = turReqs
            }
        }
        binding.turlerRv.apply {
            adapter = turlerRvAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        viewModel.turler.observe(viewLifecycleOwner, Observer { turler ->
            turlerRvAdapter.list = turler
        })
    }

    private fun setupYazarlarRv() {
        yazarlarRvAdapter = YazarlarRvAdapter(ArrayList()) { yazarReq ->
            viewModel.yazarlar.value?.let { yazarReqs ->
                yazarReqs.remove(yazarReq)
                viewModel.yazarlar.value = yazarReqs
            }
        }
        binding.yazarlarRv.apply {
            adapter = yazarlarRvAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        viewModel.yazarlar.observe(viewLifecycleOwner, Observer { yazarlar ->
            yazarlarRvAdapter.list = yazarlar
        })
    }

    private fun setupYazarEkleButton() {
        binding.yazarEkleButton.setOnClickListener {
            context?.let {
                showYazarInputDialog(it)
            }
        }
    }

    private fun setupTurEkleButton() {
        binding.turEkleButton.setOnClickListener {
            context?.let {
                showTurInputDialog(it)
            }
        }
    }

    fun showYazarInputDialog(context: Context) {
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.dialog_yazar_ekle, null)
        val ekleButton = view.findViewById<Button>(R.id.ekle)
        val vazgecButton = view.findViewById<Button>(R.id.vazgec)
        builder.setView(view)
        ekleButton.setOnClickListener {
            val adi = view.findViewById<EditText>(R.id.yazarAdi).text.toString()
            val soyadi = view.findViewById<EditText>(R.id.yazarSoyadi).text.toString()
            viewModel.yazarlar.value?.let {
                it.add(YazarReq(adi, soyadi))
            }
            builder.dismiss()
        }
        vazgecButton.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    fun showTurInputDialog(context: Context) {
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .create()
        val view = layoutInflater.inflate(R.layout.dialog_tur_ekle, null)
        val ekleButton = view.findViewById<Button>(R.id.ekle)
        val vazgecButton = view.findViewById<Button>(R.id.vazgec)
        builder.setView(view)
        ekleButton.setOnClickListener {
            val turAdi = view.findViewById<EditText>(R.id.turAdi).text.toString()
            viewModel.turler.value?.let {
                it.add(TurReq(turAdi))
            }
            builder.dismiss()
        }
        vazgecButton.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
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
                            kitapReq.isbn.let {
                                isbnEditText.setText(kitapReq.isbn.toString())
                            }
                            kitapReq.adi.let {
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
                            viewModel?.let { kitapOlusturViewModel ->

                                kitapReq.yazarlar?.let {
                                    kitapOlusturViewModel.yazarlar.value = it as ArrayList<YazarReq>
                                }
                                kitapReq.turler?.let {
                                    kitapOlusturViewModel.turler.value = it as ArrayList<TurReq>
                                }
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