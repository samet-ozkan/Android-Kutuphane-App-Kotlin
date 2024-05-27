package com.sametozkan.kutuphane.presentation.kullanici.kutuphaneler

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.kutuphane.databinding.FragmentKutuphanelerBinding
import com.sametozkan.kutuphane.presentation.kullanici.kutuphane.KutuphaneActivity
import com.sametozkan.kutuphane.util.MyResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KutuphanelerFragment : Fragment() {

    private lateinit var binding: FragmentKutuphanelerBinding
    val viewModel: KutuphanelerViewModel by viewModels()
    private lateinit var rvAdapter: KutuphanelerRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKutuphanelerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupKutuphanelerRv()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchKutuphaneler { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    rvAdapter.list = myResult.data
                }

                is MyResult.Error -> {
                    Toast.makeText(context, "Kütüphane listesi alınamadı!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupKutuphanelerRv() {
        rvAdapter = KutuphanelerRvAdapter(ArrayList()) {
            kutuphaneId ->
            val intent = Intent(context, KutuphaneActivity::class.java)
            intent.putExtra("Kütüphane ID", kutuphaneId)
            startActivity(intent)
        }
        binding.kutuphanelerRv.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@KutuphanelerFragment.context)
            setHasFixedSize(true)
        }
    }
}