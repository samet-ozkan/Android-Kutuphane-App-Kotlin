package com.sametozkan.kutuphane.presentation.kutuphane.home.teslimdurumu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.databinding.FragmentKutuphaneTeslimDurumuBinding
import com.sametozkan.kutuphane.util.KutuphaneKitapIstekleriChips
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KutuphaneTeslimDurumuFragment : Fragment() {

    private lateinit var binding: FragmentKutuphaneTeslimDurumuBinding
    private lateinit var rvAdapter: KutuphaneTeslimDurumuRvAdapter

    val viewModel: KutuphaneTeslimDurumuViewModel by viewModels()

    val teslimEdildiClickListener: (KitapKullaniciRes) -> Unit = { kitapKullaniciRes ->
        viewModel.teslimEdildi(kitapKullaniciRes.id){
            myResult -> 
            when(myResult){
                is MyResult.Success -> {
                    Toast.makeText(context, "Teslim edildi!", Toast.LENGTH_SHORT).show()
                    fetchTeslimEdilmeyenler()
                }
                is MyResult.Error -> {
                    Toast.makeText(context, "Teslim edilemedi!", Toast.LENGTH_SHORT).show()
                    println(myResult.exception.message)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKutuphaneTeslimDurumuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupSearchView()

        fetchTeslimEdilmeyenler()

        observeQuery()
    }

    private fun fetchTeslimEdilmeyenler() {
        viewModel.fetchTeslimEdilmeyenler { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    viewModel.kitaplar = myResult.data as ArrayList<KitapKullaniciRes>
                    rvAdapter.list = viewModel.filter()
                }

                is MyResult.Error -> {
                    Toast.makeText(context, "Teslim edilmeyen kitaplar alınamadı!", Toast.LENGTH_SHORT).show()
                    println(myResult.exception.message)
                }
            }
        }
    }

    private fun observeQuery() {
        viewModel.query.observe(viewLifecycleOwner) {
            rvAdapter.list = viewModel.filter()
        }
    }

    private fun setupRv() {
        if (viewModel.kitaplar.isNotEmpty()) {
            lateinit var list: List<KitapKullaniciRes>
            viewModel.query.value?.let { query ->
                list = viewModel.kitaplar.filter {
                    it.kullanici.id.equals(query) || "${it.kullanici.adi} ${it.kullanici.soyadi}".contains(
                        query,
                        ignoreCase = true
                    )
                }
            } ?: kotlin.run {
                list = viewModel.kitaplar
            }
            rvAdapter =
                KutuphaneTeslimDurumuRvAdapter(list, teslimEdildiClickListener)
        } else {
            rvAdapter = KutuphaneTeslimDurumuRvAdapter(
                ArrayList(),
                teslimEdildiClickListener,
            )
        }
        binding.recyclerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.query.value = newText
                return true
            }
        })
    }
}