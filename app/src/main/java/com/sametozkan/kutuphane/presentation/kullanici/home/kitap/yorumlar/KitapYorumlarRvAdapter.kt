package com.sametozkan.kutuphane.presentation.kullanici.home.kitap.yorumlar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.data.dto.response.KitapYorumRes
import com.sametozkan.kutuphane.databinding.ItemKitapYorumBinding

class KitapYorumlarRvAdapter :
    RecyclerView.Adapter<KitapYorumlarRvAdapter.ViewHolder> {

    var list: List<KitapYorumRes>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    constructor(list: List<KitapYorumRes>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKitapYorumBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(holder.adapterPosition))
    }

    class ViewHolder(private val binding: ItemKitapYorumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(kitapYorumRes: KitapYorumRes) {
            binding.kitapYorumRes = kitapYorumRes
            binding.spoilerGosterButton.setOnClickListener {
                binding.spoilerLayout.visibility = View.INVISIBLE
                binding.yorumTextView.visibility = View.VISIBLE
            }
        }
    }

}