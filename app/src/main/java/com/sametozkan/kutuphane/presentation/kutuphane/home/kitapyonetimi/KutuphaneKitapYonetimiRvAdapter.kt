package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.databinding.ItemKitapYonetimiBinding

class KutuphaneKitapYonetimiRvAdapter :
    RecyclerView.Adapter<KutuphaneKitapYonetimiRvAdapter.ViewHolder> {

    var list: List<KitapRes>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    constructor(list: List<KitapRes>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKitapYonetimiBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(holder.adapterPosition))
    }

    class ViewHolder(private val binding: ItemKitapYonetimiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(kitapRes: KitapRes) {
            binding.kitapRes = kitapRes
        }
    }

}