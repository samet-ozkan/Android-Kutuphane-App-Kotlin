package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle

import android.content.res.Resources.NotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sametozkan.kutuphane.databinding.FragmentKitapEkleBinding
import com.sametozkan.kutuphane.util.FragmentNameConstants
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KitapEkleFragment : Fragment() {

    private lateinit var binding: FragmentKitapEkleBinding
    private val sharedViewModel: KitapEkleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKitapEkleBinding.inflate(inflater, container, false)
        binding.viewModel = sharedViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEkleButton()
    }

    private fun setupEkleButton(){
        binding.ekleButton.setOnClickListener {
            sharedViewModel.findByIsbn { result ->
                when(result){
                    is MyResult.Success -> {
                        sharedViewModel.kitapId = result.data.id
                        sharedViewModel.saveKitapKutuphane {
                            result ->
                            when(result){
                                is MyResult.Success -> {
                                    Toast.makeText(context, "Kitap kütüphaneye eklendi.", Toast.LENGTH_SHORT).show()
                                    requireActivity().finish()
                                }
                                is MyResult.Error -> {
                                    Toast.makeText(context, "İşlem başarısız oldu.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    is MyResult.Error -> {
                        if(result.exception is NotFoundException){
                            sharedViewModel.changeFragment.postValue(FragmentNameConstants.KITAP_OLUSTUR)
                        }
                    }
                }
            }
            }
        }
    }