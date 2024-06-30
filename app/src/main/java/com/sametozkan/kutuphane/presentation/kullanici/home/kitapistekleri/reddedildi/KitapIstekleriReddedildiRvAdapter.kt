package com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri.reddedildi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.databinding.ItemKitapIstekleriReddedildiBinding

class KitapIstekleriReddedildiRvAdapter : RecyclerView.Adapter<KitapIstekleriReddedildiRvAdapter.ViewHolder> {

    var list: List<KitapKullaniciRes>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val onKutuphaneClickListener: (KutuphaneRes) -> Unit

    constructor(list: List<KitapKullaniciRes>, onKutuphaneClickListener: (KutuphaneRes) -> Unit) {
        this.list = list
        this.onKutuphaneClickListener = onKutuphaneClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKitapIstekleriReddedildiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onKutuphaneClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kitapKullaniciRes = list.get(holder.adapterPosition)
        holder.bindItem(kitapKullaniciRes)
    }

    class ViewHolder(private val binding : ItemKitapIstekleriReddedildiBinding, private val onKutuphaneClickListener: (KutuphaneRes) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(kitapKullaniciRes: KitapKullaniciRes){
            binding.kitapKullaniciRes = kitapKullaniciRes
            binding.kutuphaneAdiTv.setOnClickListener {
                onKutuphaneClickListener(kitapKullaniciRes.kutuphane)
            }
        }

    }
}