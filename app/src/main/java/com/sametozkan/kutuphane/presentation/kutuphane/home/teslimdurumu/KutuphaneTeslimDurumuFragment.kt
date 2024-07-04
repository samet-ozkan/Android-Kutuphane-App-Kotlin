package com.sametozkan.kutuphane.presentation.kutuphane.home.teslimdurumu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.checkbox.MaterialCheckBox
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.data.dto.response.KullaniciRes
import com.sametozkan.kutuphane.databinding.FragmentKutuphaneTeslimDurumuBinding
import com.sametozkan.kutuphane.presentation.bottomsheet.KitapBottomSheet
import com.sametozkan.kutuphane.presentation.bottomsheet.KullaniciBottomSheet
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.MyResult
import com.sametozkan.kutuphane.util.VerticalSpaceItemDecoration
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
                    Toast.makeText(context, "`Teslim edildi` olarak güncellendi.", Toast.LENGTH_SHORT).show()
                    fetchTeslimEdilmeyenler()
                }
                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception, parentFragmentManager, context)
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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        setupSearchView()

        fetchTeslimEdilmeyenler()

        observeQuery()
        observeIsEmpty()
        setupZamaniGecmisCheckbox()
    }

    private fun setupZamaniGecmisCheckbox(){
        binding.zamaniGecmisCheckbox.addOnCheckedStateChangedListener { checkBox, state ->
            viewModel.zamaniGecmisCheckbox.set(state == MaterialCheckBox.STATE_CHECKED)
            rvAdapter.list = viewModel.filter()
        }
    }

    private fun observeIsEmpty(){
        viewModel.isEmpty.observe(viewLifecycleOwner){
            binding.isEmpty = it
        }
    }

    private fun fetchTeslimEdilmeyenler() {
        viewModel.fetchTeslimEdilmeyenler { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    viewModel.kitaplar = myResult.data as ArrayList<KitapKullaniciRes>
                    rvAdapter.list = viewModel.filter()
                }

                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception, parentFragmentManager, context)
                }
            }
        }
    }

    private fun observeQuery() {
        viewModel.query.observe(viewLifecycleOwner) {
            rvAdapter.list = viewModel.filter()
        }
    }

    val isbnClickListener: (KitapRes) -> Unit = {
        KitapBottomSheet(it).show(childFragmentManager, "Kitap")
    }

    val kullaniciIdClickListener : (KullaniciRes) -> Unit = {
        KullaniciBottomSheet(it).show(childFragmentManager, "Kullanici")
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
                KutuphaneTeslimDurumuRvAdapter(list, teslimEdildiClickListener, isbnClickListener, kullaniciIdClickListener)
        } else {
            rvAdapter = KutuphaneTeslimDurumuRvAdapter(
                ArrayList(),
                teslimEdildiClickListener,
                isbnClickListener,
                kullaniciIdClickListener
            )
        }

        binding.recyclerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(VerticalSpaceItemDecoration(20))
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