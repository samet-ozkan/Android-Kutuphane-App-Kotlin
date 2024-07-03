package com.sametozkan.kutuphane.presentation.kullanici.home.kutuphaneler

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.kutuphane.databinding.FragmentKutuphanelerBinding
import com.sametozkan.kutuphane.presentation.kullanici.kutuphane.KutuphaneActivity
import com.sametozkan.kutuphane.util.ErrorUtil
import com.sametozkan.kutuphane.util.MyResult
import com.sametozkan.kutuphane.util.VerticalSpaceItemDecoration
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
                    binding.isEmpty = myResult.data.isEmpty()
                    rvAdapter.list = myResult.data
                }

                is MyResult.Error -> {
                    ErrorUtil.showErrorDialog(myResult.responseCode, myResult.exception, parentFragmentManager, context)
                }
            }
        }
    }

    private fun setupKutuphanelerRv() {
        rvAdapter = KutuphanelerRvAdapter(ArrayList()) {
            kutuphaneId ->
            val intent = Intent(requireContext(), KutuphaneActivity::class.java)
            intent.putExtra("Kütüphane ID", kutuphaneId)
            startActivity(intent)
        }
        binding.kutuphanelerRv.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@KutuphanelerFragment.context)
            addItemDecoration(VerticalSpaceItemDecoration(20))
            setHasFixedSize(true)
        }
    }
}