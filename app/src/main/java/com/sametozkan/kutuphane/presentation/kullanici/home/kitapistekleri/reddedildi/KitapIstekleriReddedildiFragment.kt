package com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri.onaylandi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.kutuphane.databinding.FragmentKitapIstekleriReddedildiBinding
import com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri.reddedildi.KitapIstekleriReddedildiViewModel
import com.sametozkan.kutuphane.presentation.kullanici.kutuphane.KutuphaneActivity
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KitapIstekleriReddedildiFragment : Fragment() {

    private lateinit var binding: FragmentKitapIstekleriReddedildiBinding
    private lateinit var rvAdapter: KitapIstekleriOnaylandiRvAdapter
    val viewModel: KitapIstekleriReddedildiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKitapIstekleriReddedildiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
    }

    override fun onStart() {
        super.onStart()
        refreshList()
    }

    private fun refreshList() {
        viewModel.fetchReddedilenler { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    binding.isEmpty = myResult.data.isEmpty()
                    rvAdapter.list = myResult.data
                }

                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception, parentFragmentManager, context)
                }
            }
        }
    }

    private fun setupRv() {
        rvAdapter = KitapIstekleriOnaylandiRvAdapter(ArrayList()) { kutuphaneRes ->
            val intent = Intent(context, KutuphaneActivity::class.java)
            intent.putExtra("Kütüphane ID", kutuphaneRes.id)
            startActivity(intent)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            setHasFixedSize(true)
        }
    }
}