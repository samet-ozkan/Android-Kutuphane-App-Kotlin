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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
    @BindingAdapter("tarih")
    fun setTextFromTime(textView: TextView, time: List<Int>?) {
        if (time != null && time.size >= 7) {
            val year = time[0]
            val month = time[1]
            val dayOfMonth = time[2]
            val hour = time[3]
            val minute = time[4]
            val second = time[5]
            val millisecond = time[6]

            val calendar = Calendar.getInstance()
            calendar.set(year, month - 1, dayOfMonth, hour, minute, second)
            calendar.set(Calendar.MILLISECOND, millisecond)

            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val formattedDateTime = sdf.format(calendar.time)

            textView.text = formattedDateTime
        } else {
            textView.text = "Mevcut değil"
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
    @BindingAdapter("yazarlar")
    fun setYazarlar(view: TextView, list: List<YazarRes>) {
        if (list != null && !list.isEmpty()) {
            view.setText(list.get(0).adi + " " + list.get(0).soyadi)
        }
    }

    //Burda kaldim. Tasarima gec, kod sonra halledilir

    @JvmStatic
    @BindingAdapter("turler")
    fun setTurler(view: TextView, list: List<TurRes>) {
        if (list != null && !list.isEmpty()) {
            view.setText(list.get(0).tur)
        }
    }

    @JvmStatic
    @BindingAdapter("yazarItems")
    fun setYazarItems(listView: ListView, items: List<YazarRes>?) {
        items?.let {
            val fullNames = it.map { yazar -> "${yazar.adi} ${yazar.soyadi}" }
            val adapter = ArrayAdapter(listView.context, android.R.layout.simple_list_item_1, fullNames)
            listView.adapter = adapter
        }
    }

    @JvmStatic
    @BindingAdapter("turItems")
    fun setTurItems(listView: ListView, items: List<TurRes>?) {
        items?.let {
            val turNames = it.map { tur -> "${tur.tur}" }
            val adapter = ArrayAdapter(listView.context, android.R.layout.simple_list_item_1, turNames)
            listView.adapter = adapter
        }
    }
}