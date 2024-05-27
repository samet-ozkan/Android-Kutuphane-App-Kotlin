package com.sametozkan.kutuphane.presentation.kullanici.kutuphane.yorumlar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.data.dto.response.KutuphaneYorumRes
import com.sametozkan.kutuphane.databinding.ItemKutuphaneYorumBinding

class KutuphaneYorumlarRvAdapter :
    RecyclerView.Adapter<KutuphaneYorumlarRvAdapter.ViewHolder> {

    var list: List<KutuphaneYorumRes>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    constructor(list: List<KutuphaneYorumRes>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKutuphaneYorumBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(holder.adapterPosition))
    }

    class ViewHolder(private val binding: ItemKutuphaneYorumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(kutuphaneYorumRes: KutuphaneYorumRes) {
            binding.kutuphaneYorumRes = kutuphaneYorumRes
        }
    }

}