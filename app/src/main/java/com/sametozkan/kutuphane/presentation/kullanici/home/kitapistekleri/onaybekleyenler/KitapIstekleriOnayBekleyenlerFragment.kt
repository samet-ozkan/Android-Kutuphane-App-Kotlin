package com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri.onaybekleyenler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.kutuphane.databinding.FragmentKitapIstekleriOnayBekleyenlerBinding
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KitapIstekleriOnayBekleyenlerFragment : Fragment() {

    private lateinit var binding: FragmentKitapIstekleriOnayBekleyenlerBinding
    private lateinit var rvAdapter: OnayBekleyenlerRvAdapter
    val viewModel: OnayBekleyenlerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKitapIstekleriOnayBekleyenlerBinding.inflate(inflater, container, false)
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
        viewModel.fetchOnayBekleyenler { myResult ->
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
        rvAdapter = OnayBekleyenlerRvAdapter(ArrayList()) { kitapKullaniciRes ->
            viewModel.deleteById(kitapKullaniciRes.id) {
            myResult ->
            when(myResult) {
                is MyResult.Success -> {
                    Toast.makeText(context, "Kitap isteği başarıyla iptal edildi.", Toast.LENGTH_SHORT).show()
                    refreshList()
                }
                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception, parentFragmentManager, context)
                }
            }
            }
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            setHasFixedSize(true)
        }
    }
}