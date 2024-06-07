package com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri.onaylandi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.databinding.ItemKitapIstekleriMevcutBinding

class KitapIstekleriOnaylandiRvAdapter : RecyclerView.Adapter<KitapIstekleriOnaylandiRvAdapter.ViewHolder> {

    var list: List<KitapKullaniciRes>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val itemClickListener: (KitapKullaniciRes) -> Unit

    constructor(list: List<KitapKullaniciRes>, itemClickListener: (KitapKullaniciRes) -> Unit) {
        this.list = list
        this.itemClickListener = itemClickListener
    }

    class ViewHolder(private val binding : ItemKitapIstekleriMevcutBinding, private val itemClickListener: (KitapKullaniciRes) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(kitapKullaniciRes: KitapKullaniciRes){
            binding.kitapKullaniciRes = kitapKullaniciRes
            // TODO: Click listener ekle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKitapIstekleriMevcutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kitapKullaniciRes = list.get(holder.adapterPosition)
        holder.bindItem(kitapKullaniciRes)
    }
}