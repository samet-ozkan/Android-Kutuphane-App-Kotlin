package com.sametozkan.kutuphane.presentation.kullanici.kutuphane

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.databinding.ItemSonKitapAlanlarBinding

class SonKitapAlanlarRvAdapter : RecyclerView.Adapter<SonKitapAlanlarRvAdapter.ViewHolder> {

    var list : List<KitapKullaniciRes>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val onKitapClickListener : (KitapRes, KutuphaneRes) -> Unit

    constructor(list : List<KitapKullaniciRes>, onKitapClickListener: (KitapRes, KutuphaneRes) -> Unit){
        this.list = list
        this.onKitapClickListener = onKitapClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSonKitapAlanlarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onKitapClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[holder.adapterPosition])
    }

    class ViewHolder(val binding : ItemSonKitapAlanlarBinding, val onKitapClickListener: (KitapRes, KutuphaneRes) -> Unit) : RecyclerView.ViewHolder(binding.root){

        fun bindItem(kitapKullaniciRes: KitapKullaniciRes){
            binding.kitapKullaniciRes = kitapKullaniciRes
            binding.kitapAdi.setOnClickListener {
                onKitapClickListener(kitapKullaniciRes.kitap, kitapKullaniciRes.kutuphane)
            }
        }
    }
}