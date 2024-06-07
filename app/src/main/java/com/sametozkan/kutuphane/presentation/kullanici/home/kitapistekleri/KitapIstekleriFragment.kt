package com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sametozkan.kutuphane.databinding.FragmentKitapIstekleriBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KitapIstekleriFragment: Fragment() {

    private lateinit var binding : FragmentKitapIstekleriBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKitapIstekleriBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabLayoutMediator()
    }

    private fun setupTabLayoutMediator(){
        val adapter = KitapIstekleriVpAdapter(parentFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position){
                0 -> {
                    tab.text = "Onay Bekleyenler"
                }
                1 -> {
                    tab.text = "Mevcut"
                }
                2 -> {
                    tab.text = "Geçmiş"
                }
            }
        }.attach()
    }
}