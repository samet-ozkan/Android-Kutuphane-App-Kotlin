package com.sametozkan.kutuphane.presentation.kutuphane.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.databinding.ActivityKutuphaneHomeBinding
import com.sametozkan.kutuphane.domain.usecase.MyResult
import com.sametozkan.kutuphane.util.FragmentNameConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KutuphaneHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKutuphaneHomeBinding
    val viewModel: KutuphaneHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKutuphaneHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setChangeFragmentObserver()
        setNavigationDrawer()
        setNavigationItemClickListener()
        setNavigationViewHeader()
    }

    private fun setNavigationDrawer() {
        binding.toolbar.setNavigationIcon(R.drawable.baseline_dehaze_24)
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
    }

    private fun setNavigationItemClickListener() {
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuKitapYonetimi -> viewModel.changeFragment.value =
                    FragmentNameConstants.KITAP_YONETIMI

                else -> false
            }
            true
        }
    }

    private fun setNavigationViewHeader() {
        viewModel.getKutuphane { myResult ->
            when (myResult) {
                is MyResult.Success -> {
                    binding.kutuphaneRes = myResult.data
                }

                is MyResult.Error -> {
                    val exception = myResult.exception
                    println(exception)
                }
            }
        }
    }

    private fun setChangeFragmentObserver() {
        viewModel.changeFragment.observe(this, Observer { value ->

            /*when (value) {
                FragmentNameConstants.KITAP_EKLE -> replaceFragment()
            }
             */

        })
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, fragment).commit()
        binding.toolbarTitle.text = title
    }
}