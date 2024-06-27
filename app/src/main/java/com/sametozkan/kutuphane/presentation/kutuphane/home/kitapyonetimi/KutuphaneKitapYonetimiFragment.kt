package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.kutuphane.databinding.FragmentKutuphaneKitapYonetimiBinding
import com.sametozkan.kutuphane.presentation.kutuphane.home.KutuphaneHomeViewModel
import com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle.KitapEkleActivity
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KutuphaneKitapYonetimiFragment : Fragment() {

    private lateinit var binding: FragmentKutuphaneKitapYonetimiBinding
    private lateinit var rvAdapter: KutuphaneKitapYonetimiRvAdapter
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
        setupFab()
        setupKitaplarRv()
    }

    override fun onStart() {
        super.onStart()
        sharedViewModel.fetchKutuphane { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    rvAdapter.list = myResult.data.kitaplar
                }

                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception, parentFragmentManager, context)
                }
            }
        }
    }

    private fun setupFab() {
        binding.addFab.setOnClickListener {
            val intent = Intent(requireActivity(), KitapEkleActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setupKitaplarRv() {
        rvAdapter = KutuphaneKitapYonetimiRvAdapter(ArrayList())
        binding.kitapRecyclerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@KutuphaneKitapYonetimiFragment.context)
            setHasFixedSize(true)
        }
    }

}