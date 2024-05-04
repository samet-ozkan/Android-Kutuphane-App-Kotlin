package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle.kitapolustur

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.data.dto.request.YazarReq
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.databinding.ItemYazarEkleBinding

class YazarlarRvAdapter : RecyclerView.Adapter<YazarlarRvAdapter.ViewHolder> {

    var list: List<YazarReq>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val itemClickListener: (YazarReq) -> Unit

    constructor(list: List<YazarReq>, itemClickListener: (YazarReq) -> Unit) {
        this.list = list
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemYazarEkleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(holder.adapterPosition))
    }

    class ViewHolder(private val binding: ItemYazarEkleBinding, private val itemClickListener: (YazarReq) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(yazarReq: YazarReq) {
            binding.yazarReq = yazarReq
            binding.remove.setOnClickListener {
                itemClickListener(yazarReq)
            }
        }
    }
}