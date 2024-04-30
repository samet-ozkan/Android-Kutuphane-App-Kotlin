package com.sametozkan.kutuphane.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.navigation.NavigationView
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:bindKutuphane")
    fun bindKutuphane(navigationView : NavigationView, kutuphaneRes: KutuphaneRes?){
        kutuphaneRes?.let {
            val headerView = navigationView.getHeaderView(0)
            val name = headerView.findViewById<TextView>(R.id.fullName)
            val email = headerView.findViewById<TextView>(R.id.email)
            name.text = kutuphaneRes.adi
            email.text = kutuphaneRes.account.email
        }
    }
}