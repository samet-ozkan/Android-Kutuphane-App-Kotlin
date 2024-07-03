package com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri.onaybekleyenler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.databinding.ItemKitapIstekleriOnayBekleyenlerBinding

class OnayBekleyenlerRvAdapter : RecyclerView.Adapter<OnayBekleyenlerRvAdapter.ViewHolder> {

    var list: List<KitapKullaniciRes>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val iptalEtClickListener: (KitapKullaniciRes) -> Unit

    val kutuphaneClickListener: (KutuphaneRes) -> Unit

    val isbnClickListener: (KitapRes) -> Unit

    constructor(
        list: List<KitapKullaniciRes>,
        onIptalEtClickListener: (KitapKullaniciRes) -> Unit,
        onKutuphaneClickListener: (KutuphaneRes) -> Unit,
        isbnClickListener: (KitapRes) -> Unit
    ) {
        this.list = list
        this.iptalEtClickListener = onIptalEtClickListener
        this.kutuphaneClickListener = onKutuphaneClickListener
        this.isbnClickListener = isbnClickListener
    }

    class ViewHolder(
        private val binding: ItemKitapIstekleriOnayBekleyenlerBinding,
        private val iptalEtClickListener: (KitapKullaniciRes) -> Unit,
        private val kutuphaneClickListener: (KutuphaneRes) -> Unit,
        private val isbnClickListener: (KitapRes) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(kitapKullaniciRes: KitapKullaniciRes) {
            binding.kitapKullaniciRes = kitapKullaniciRes
            binding.iptalEtButton.setOnClickListener {
                iptalEtClickListener(kitapKullaniciRes)
            }
            binding.kutuphaneAdiTv.setOnClickListener {
                kutuphaneClickListener(kitapKullaniciRes.kutuphane)
            }
            binding.kitapItem.isbnTextView.setOnClickListener {
                isbnClickListener(kitapKullaniciRes.kitap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKitapIstekleriOnayBekleyenlerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(
            binding,
            iptalEtClickListener,
            kutuphaneClickListener,
            isbnClickListener
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kitapKullaniciRes = list.get(holder.adapterPosition)
        holder.bindItem(kitapKullaniciRes)
    }
}