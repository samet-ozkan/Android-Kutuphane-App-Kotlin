package com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri.onaybekleyenler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.databinding.ItemKitapIstekleriOnayBekleyenlerBinding

class OnayBekleyenlerRvAdapter : RecyclerView.Adapter<OnayBekleyenlerRvAdapter.ViewHolder> {

    var list: List<KitapKullaniciRes>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val onIptalEtClickListener: (KitapKullaniciRes) -> Unit

    val onKutuphaneClickListener: (KutuphaneRes) -> Unit

    constructor(list: List<KitapKullaniciRes>, onIptalEtClickListener: (KitapKullaniciRes) -> Unit, onKutuphaneClickListener: (KutuphaneRes) -> Unit) {
        this.list = list
        this.onIptalEtClickListener = onIptalEtClickListener
        this.onKutuphaneClickListener = onKutuphaneClickListener
    }

    class ViewHolder(private val binding : ItemKitapIstekleriOnayBekleyenlerBinding, private val onIptalEtClickListener: (KitapKullaniciRes) -> Unit,
    private val onKutuphaneClickListener: (KutuphaneRes) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(kitapKullaniciRes: KitapKullaniciRes){
            binding.kitapKullaniciRes = kitapKullaniciRes
            binding.iptalEtButton.setOnClickListener {
                onIptalEtClickListener(kitapKullaniciRes)
            }
            binding.kutuphaneAdiTv.setOnClickListener {
                onKutuphaneClickListener(kitapKullaniciRes.kutuphane)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKitapIstekleriOnayBekleyenlerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onIptalEtClickListener, onKutuphaneClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kitapKullaniciRes = list.get(holder.adapterPosition)
        holder.bindItem(kitapKullaniciRes)
    }
}