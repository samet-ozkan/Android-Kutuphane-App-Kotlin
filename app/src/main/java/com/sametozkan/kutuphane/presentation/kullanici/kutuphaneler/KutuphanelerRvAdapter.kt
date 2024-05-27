package com.sametozkan.kutuphane.presentation.kullanici.kutuphaneler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.databinding.ItemKutuphaneBinding

class KutuphanelerRvAdapter : RecyclerView.Adapter<KutuphanelerRvAdapter.ViewHolder> {

    var list: List<KutuphaneRes>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val itemClickListener: (Long) -> Unit

    constructor(list: List<KutuphaneRes>, itemClickListener: (Long) -> Unit) {
        this.list = list
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemKutuphaneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(holder.adapterPosition))
    }

    class ViewHolder(
        private val binding: ItemKutuphaneBinding,
        private val itemClickListener: (Long) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(kutuphaneRes: KutuphaneRes) {
            binding.kutuphane = kutuphaneRes
            binding.linearLayout.setOnClickListener {
                itemClickListener(kutuphaneRes.id)
            }
        }
    }
}