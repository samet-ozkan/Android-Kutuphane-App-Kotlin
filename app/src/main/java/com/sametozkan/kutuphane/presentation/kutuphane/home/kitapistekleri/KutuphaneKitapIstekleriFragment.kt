package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapistekleri

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
import com.sametozkan.kutuphane.databinding.FragmentKutuphaneKitapIstekleriBinding
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.KutuphaneKitapIstekleriChips
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KutuphaneKitapIstekleriFragment : Fragment() {

    private lateinit var binding: FragmentKutuphaneKitapIstekleriBinding
    private lateinit var rvAdapter: KutuphaneKitapIstekleriRvAdapter

    val viewModel: KutuphaneKitapIstekleriViewModel by viewModels()

    val onaylaClickListener: (KitapKullaniciRes) -> Unit = { kitapKullaniciRes ->
        viewModel.kitapIstegiOnayla(kitapKullaniciRes.id) { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    Toast.makeText(context, "Onaylandı!", Toast.LENGTH_SHORT).show()
                    fetchIstekler()
                }

                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception.message, parentFragmentManager, context)
                }
            }
        }
    }

    val reddetClickListener: (KitapKullaniciRes) -> Unit = { kitapKullaniciRes ->
        viewModel.kitapIstegiReddet(kitapKullaniciRes.id) { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    Toast.makeText(context, "Reddedildi!", Toast.LENGTH_SHORT).show()
                    fetchIstekler()
                }

                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception.message, parentFragmentManager, context)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKutuphaneKitapIstekleriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupSearchView()
        setupChipGroup()

        fetchIstekler()

        observeQuery()
        observeChips()
    }

    private fun observeChips() {
        viewModel.chips.observe(viewLifecycleOwner) {
            rvAdapter.list = viewModel.filter()
        }
    }

    private fun setupChipGroup() {
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chips: ArrayList<KutuphaneKitapIstekleriChips> = ArrayList()

            if (checkedIds.contains(R.id.bekleyenChip)) {
                chips.add(KutuphaneKitapIstekleriChips.BEKLEYEN)
            }
            if (checkedIds.contains(R.id.onaylandiChip)) {
                chips.add(KutuphaneKitapIstekleriChips.ONAYLANDI)
            }
            if (checkedIds.contains(R.id.reddedildiChip)) {
                chips.add(KutuphaneKitapIstekleriChips.REDDEDILDI)
            }

            viewModel.chips.value = chips
        }
    }

    private fun fetchIstekler() {
        viewModel.fetchIstekler { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    viewModel.istekler = myResult.data as ArrayList<KitapKullaniciRes>
                    rvAdapter.list = viewModel.filter()
                }

                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception.message, parentFragmentManager, context)
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
        if (viewModel.istekler.isNotEmpty()) {
            lateinit var list: List<KitapKullaniciRes>
            viewModel.query.value?.let { query ->
                list = viewModel.istekler.filter {
                    it.kullanici.id.equals(query) || "${it.kullanici.adi} ${it.kullanici.soyadi}".contains(
                        query,
                        ignoreCase = true
                    )
                }
            } ?: kotlin.run {
                list = viewModel.istekler
            }
            rvAdapter =
                KutuphaneKitapIstekleriRvAdapter(list, onaylaClickListener, reddetClickListener)
        } else {
            rvAdapter = KutuphaneKitapIstekleriRvAdapter(
                ArrayList(),
                onaylaClickListener,
                reddetClickListener
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