package com.sametozkan.kutuphane.presentation.kutuphane.home

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.databinding.ActivityKutuphaneHomeBinding
import com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.KutuphaneKitapYonetimiFragment
import com.sametozkan.kutuphane.util.MyResult
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
        binding.viewModel = viewModel

        setChangeFragmentObserver()
        setNavigationDrawer()
        setNavigationHeader()
        setNavigationItemClickListener()
    }

    private fun setNavigationHeader() {
        viewModel.fetchKutuphane { myResult ->
            when(myResult){
                is MyResult.Success -> {
                    val kutuphane = myResult.data
                    binding.navigationView.getHeaderView(0).apply {
                        findViewById<TextView>(R.id.fullName).text = kutuphane.adi
                        findViewById<TextView>(R.id.email).text = kutuphane.account.email
                    }
                }
                is MyResult.Error -> {
                    myResult.exception.printStackTrace()
                }
            }
        }
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
                R.id.menuKitapYonetimi -> {
                    binding.drawerLayout.close()
                    viewModel.changeFragment.value =
                        FragmentNameConstants.KITAP_YONETIMI
                }

                else -> false
            }
            true
        }
    }

    private fun setChangeFragmentObserver() {
        viewModel.changeFragment.observe(this, Observer { value ->

            when (value) {
                FragmentNameConstants.KITAP_YONETIMI -> replaceFragment(
                    KutuphaneKitapYonetimiFragment(),
                    "Kitap Yönetimi"
                )
            }

        })
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, fragment).commit()
        binding.toolbarTitle.text = title
    }
}