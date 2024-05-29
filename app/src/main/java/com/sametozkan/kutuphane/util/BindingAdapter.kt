package com.sametozkan.kutuphane.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import com.sametozkan.kutuphane.data.dto.response.TurRes
import com.sametozkan.kutuphane.data.dto.response.YazarRes

object BindingAdapter {

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

    @JvmStatic
    @BindingAdapter("app:yazarItems")
    fun setYazarItems(listView: ListView, items: List<YazarRes>?) {
        items?.let {
            val fullNames = it.map { yazar -> "${yazar.adi} ${yazar.soyadi}" }
            val adapter = ArrayAdapter(listView.context, android.R.layout.simple_list_item_1, fullNames)
            listView.adapter = adapter
        }
    }

    @JvmStatic
    @BindingAdapter("app:turItems")
    fun setTurItems(listView: ListView, items: List<TurRes>?) {
        items?.let {
            val turNames = it.map { tur -> "${tur.tur}" }
            val adapter = ArrayAdapter(listView.context, android.R.layout.simple_list_item_1, turNames)
            listView.adapter = adapter
        }
    }
}