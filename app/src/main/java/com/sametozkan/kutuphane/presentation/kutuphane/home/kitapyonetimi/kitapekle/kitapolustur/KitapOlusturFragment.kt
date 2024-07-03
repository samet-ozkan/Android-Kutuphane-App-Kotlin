package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle.kitapolustur

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
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
import com.sametozkan.kutuphane.presentation.dialog.SuccessDialog
import com.sametozkan.kutuphane.presentation.dialog.TurEkleDialog
import com.sametozkan.kutuphane.presentation.dialog.YazarEkleDialog
import com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle.KitapEkleViewModel
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.MyResult
import com.sametozkan.kutuphane.util.VerticalSpaceItemDecoration
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
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupIsbn()
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
            addItemDecoration(VerticalSpaceItemDecoration(20))
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
            addItemDecoration(VerticalSpaceItemDecoration(20))
            setHasFixedSize(true)
        }
        viewModel.yazarlar.observe(viewLifecycleOwner, Observer { yazarlar ->
            yazarlarRvAdapter.list = yazarlar
        })
    }

    private fun setupYazarEkleButton() {
        binding.yazarEkleButton.setOnClickListener {
            showYazarInputDialog()
        }
    }

    private fun setupTurEkleButton() {
        binding.turEkleButton.setOnClickListener {
            showTurInputDialog()
        }
    }

    fun showYazarInputDialog() {
        val dialog = YazarEkleDialog { yazarAdi, yazarSoyadi ->
            viewModel.yazarlar.value?.let {
                it.add(YazarReq(yazarAdi, yazarSoyadi))
                viewModel.yazarlar.value = it
            }
        }
        dialog.show(childFragmentManager, "Yazar Ekle")
    }

    fun showTurInputDialog() {
        val dialog = TurEkleDialog { turAdi ->
            viewModel.turler.value?.let {
                it.add(TurReq(turAdi))
                viewModel.turler.value = it
            }
        }
        dialog.show(childFragmentManager, "Tur Ekle")
    }

    private fun setupOlusturButton() {
        binding.olusturButton.setOnClickListener {
            viewModel.olustur { myResult ->
                when (myResult) {
                    is MyResult.Success -> {
                        SuccessDialog("Kitap başarıyla oluşturuldu ve kütüphaneniz ile ilişkilendirildi.") { successDialog ->
                            successDialog.dismiss()
                            requireActivity().finish()
                        }
                    }

                    is MyResult.Error -> {
                        ErrorUtil.showErrorDialog(
                            myResult.responseCode,
                            myResult.exception,
                            parentFragmentManager,
                            context
                        )

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
                            "Kitap bilgileri Google Books'dan getirildi.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is MyResult.Error -> {
                        ErrorUtil.showErrorDialog(
                            result.responseCode,
                            result.exception,
                            parentFragmentManager,
                            context
                        )
                    }
                }
            }
        }
    }
}