package com.sametozkan.kutuphane.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.data.dto.response.TurRes
import com.sametozkan.kutuphane.data.dto.response.YazarRes

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:bindKutuphane")
    fun bindKutuphane(navigationView: NavigationView, kutuphaneRes: KutuphaneRes?) {
        kutuphaneRes?.let {
            val headerView = navigationView.getHeaderView(0)
            val name = headerView.findViewById<TextView>(R.id.fullName)
            val email = headerView.findViewById<TextView>(R.id.email)
            name.text = kutuphaneRes.adi
            email.text = kutuphaneRes.account.email
        }
    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun setText(view: EditText, value: MutableLiveData<Long>?) {
        value?.let {
            val currentValue = it.value?.toString() ?: ""
            if (view.text.toString() != currentValue) {
                view.setText(currentValue)
            }
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "android:text")
    fun getText(view: EditText): Long {
        return try {
            view.text.toString().toLong()
        } catch (e: NumberFormatException) {
            0L
        }
    }

    @JvmStatic
    @BindingAdapter("android:textAttrChanged")
    fun setListener(view: EditText, listener: InverseBindingListener?) {
        if (listener != null) {
            view.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    listener.onChange()
                }
            })
        }
    }

    @JvmStatic
    @BindingAdapter("app:yazarlar")
    fun setYazarlar(view: TextView, list: List<YazarRes>) {
        if (list != null && !list.isEmpty()) {
            view.setText(list.get(0).adi + " " + list.get(0).soyadi)
        }
    }

    @JvmStatic
    @BindingAdapter("app:turler")
    fun setTurler(view: TextView, list: List<TurRes>) {
        if (list != null && !list.isEmpty()) {
            view.setText(list.get(0).tur)
        }
    }
}