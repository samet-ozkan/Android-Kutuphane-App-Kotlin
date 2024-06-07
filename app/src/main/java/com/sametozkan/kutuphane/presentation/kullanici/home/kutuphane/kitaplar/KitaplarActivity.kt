package com.sametozkan.kutuphane.presentation.kullanici.home.kutuphane.kitaplar

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.databinding.ActivityKitaplarBinding
import com.sametozkan.kutuphane.presentation.kullanici.home.kitap.KitapActivity
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KitaplarActivity : AppCompatActivity() {

    lateinit var binding: ActivityKitaplarBinding
    private lateinit var rvAdapter: KitaplarRvAdapter

    val viewModel: KitaplarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKitaplarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupKutuphaneId()
        fetchKitaplar()
        setupRv()
        setupSearchView()
        observeQuery()
    }

    private fun observeQuery() {
        viewModel.query.observe(this) { query ->
            if (query == null || query.isEmpty()) {
                rvAdapter.list = viewModel.kitaplar
            } else {
                val filteredList =
                    viewModel.kitaplar.filter { it.adi.contains(query, ignoreCase = true) }
                rvAdapter.list = filteredList
            }
        }
    }

    private fun fetchKitaplar() {
        viewModel.fetchKutuphane { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    viewModel.kitaplar = myResult.data.kitaplar as ArrayList<KitapRes>
                    setupRv()
                }

                is MyResult.Error -> {
                    println(myResult.exception.message)
                }
            }
        }
    }

    private fun setupKutuphaneId() {
        if (viewModel.kutuphaneId == null) {
            val kutuphaneId = intent.getLongExtra("Kütüphane ID", 0)
            if (kutuphaneId != 0L)
                viewModel.kutuphaneId = kutuphaneId
            else
                println("Intent ile kütüphane id alınamadı!")
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.query.value = newText
                return true
            }
        })
    }

    val kitapClickListener: (KitapRes) -> Unit = { kitapRes ->
        val intent = Intent(this, KitapActivity::class.java)
        intent.putExtra("Kütüphane ID", viewModel.kutuphaneId)
        intent.putExtra("Kitap ID", kitapRes.id)
        startActivity(intent)
    }

    private fun setupRv() {
        if (viewModel.kitaplar.isNotEmpty()) {
            lateinit var list: List<KitapRes>
            viewModel.query.value?.let { query ->
                list = viewModel.kitaplar.filter { it.adi.contains(query, ignoreCase = true) }
            } ?: kotlin.run {
                list = viewModel.kitaplar
            }
            rvAdapter = KitaplarRvAdapter(list, kitapClickListener)
        } else {
            rvAdapter = KitaplarRvAdapter(ArrayList(), kitapClickListener)
        }
        binding.kitaplarRv.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
}