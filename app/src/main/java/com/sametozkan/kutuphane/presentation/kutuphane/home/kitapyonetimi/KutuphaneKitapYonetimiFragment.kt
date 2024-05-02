package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.databinding.FragmentKutuphaneKitapYonetimiBinding
import com.sametozkan.kutuphane.presentation.kutuphane.home.KutuphaneHomeViewModel
import com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle.KitapEkleActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KutuphaneKitapYonetimiFragment : Fragment() {

    private lateinit var binding: FragmentKutuphaneKitapYonetimiBinding
    val sharedViewModel: KutuphaneHomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKutuphaneKitapYonetimiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeKutuphane()
        setFab()
    }

    private fun setFab() {
        binding.addFab.setOnClickListener {
            val intent = Intent(requireActivity(), KitapEkleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeKutuphane() {
        if (sharedViewModel.kutuphane.value == null) {
            sharedViewModel.kutuphane.observe(viewLifecycleOwner, Observer {
                it?.let {
                    setRecyclerView(it.kitaplar)
                }
            })
        } else {
            setRecyclerView(sharedViewModel.kutuphane.value!!.kitaplar)
        }
    }

    private fun setRecyclerView(list: List<KitapRes>) {
        val rvAdapter = KutuphaneKitapYonetimiRvAdapter(list)
        binding.kitapRecyclerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@KutuphaneKitapYonetimiFragment.context)
            setHasFixedSize(true)
        }
    }

}