package com.sametozkan.kutuphane.presentation.kullanici.kutuphane.kitaplar

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.databinding.ActivityKitaplarBinding
import com.sametozkan.kutuphane.presentation.bottomsheet.KitapBottomSheet
import com.sametozkan.kutuphane.presentation.kullanici.kitap.KitapActivity
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.LoadingManager
import com.sametozkan.kutuphane.util.MyResult
import com.sametozkan.kutuphane.util.VerticalSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KitaplarActivity : AppCompatActivity() {

    lateinit var binding: ActivityKitaplarBinding
    private lateinit var rvAdapter: KitaplarRvAdapter

    val viewModel: KitaplarViewModel by viewModels()

    val kitapClickListener: (KitapRes) -> Unit = { kitapRes ->
        val intent = Intent(this, KitapActivity::class.java)
        intent.putExtra("Kütüphane ID", viewModel.kutuphaneId)
        intent.putExtra("Kitap ID", kitapRes.id)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKitaplarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupKutuphaneId()
        fetchKitaplar()
        setupRv()
        setupSearchView()
        observeQuery()

        observeLoading()
        setupBackButton()
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeLoading() {
        LoadingManager.loading.observe(this) {
            binding.isLoading = it
        }
    }

    private fun observeQuery() {
        viewModel.query.observe(this) { query ->
            if (query == null || query.isEmpty()) {
                rvAdapter.list = viewModel.kitaplar
            } else {
                val filteredList =
                    viewModel.kitaplar.filter {
                        it.adi.contains(query, ignoreCase = true)
                                || it.isbn.toString().contains(query)
                    }
                rvAdapter.list = filteredList
            }
        }
    }

    private fun fetchKitaplar() {
        viewModel.fetchKutuphane { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    binding.isEmpty = myResult.data.kitaplar.isEmpty()
                    viewModel.kitaplar = myResult.data.kitaplar as ArrayList<KitapRes>
                    setupRv()
                }

                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(
                        myResult.responseCode,
                        myResult.exception,
                        supportFragmentManager,
                        this
                    )
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

    val isbnClickListener: (KitapRes) -> Unit = { kitapRes ->
        KitapBottomSheet(kitapRes).show(supportFragmentManager, "Kitap")
    }

    private fun setupRv() {
        if (viewModel.kitaplar.isNotEmpty()) {
            lateinit var list: List<KitapRes>
            viewModel.query.value?.let { query ->
                list = viewModel.kitaplar.filter { it.adi.contains(query, ignoreCase = true) || it.isbn.toString().contains(query) }
            } ?: kotlin.run {
                list = viewModel.kitaplar
            }
            rvAdapter = KitaplarRvAdapter(list, kitapClickListener, isbnClickListener)
        } else {
            rvAdapter = KitaplarRvAdapter(ArrayList(), kitapClickListener, isbnClickListener)
        }
        binding.kitaplarRv.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalSpaceItemDecoration(20))
            setHasFixedSize(true)
        }
    }
}