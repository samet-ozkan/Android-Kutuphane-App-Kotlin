package com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri.onaylandi.KitapIstekleriReddedildiFragment
import com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri.onaylandi.KitapIstekleriOnaylandiFragment
import com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri.onaybekleyenler.KitapIstekleriOnayBekleyenlerFragment

private const val NUM_TABS = 3

class KitapIstekleriVpAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return KitapIstekleriOnayBekleyenlerFragment()
            1 -> return KitapIstekleriOnaylandiFragment()
            2 -> return KitapIstekleriReddedildiFragment()
        }
        return KitapIstekleriOnayBekleyenlerFragment()
    }
}