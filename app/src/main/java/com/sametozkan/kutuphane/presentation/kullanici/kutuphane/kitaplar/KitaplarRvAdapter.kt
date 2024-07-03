package com.sametozkan.kutuphane.presentation.kullanici.kutuphane.kitaplar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.databinding.ItemKitapBinding

class KitaplarRvAdapter :
    RecyclerView.Adapter<KitaplarRvAdapter.ViewHolder> {

    var list: List<KitapRes>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val itemClickListener: (KitapRes) -> Unit

    val isbnClickListener: (KitapRes) -> Unit

    constructor(list: List<KitapRes>, itemClickListener: (KitapRes) -> Unit, isbnClickListener: (KitapRes) -> Unit) {
        this.list = list
        this.itemClickListener = itemClickListener
        this.isbnClickListener = isbnClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKitapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClickListener, isbnClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[holder.adapterPosition])
    }

    class ViewHolder(
        private val binding: ItemKitapBinding,
        private val itemClickListener: (KitapRes) -> Unit,
        private val isbnClickListener: (KitapRes) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(kitapRes: KitapRes) {
            binding.kitapRes = kitapRes
            binding.linkColor = android.graphics.Color.parseColor("#3700B3")
            binding.isbnTextView.setOnClickListener {
                isbnClickListener(kitapRes)
            }
            binding.constraintLayout.setOnClickListener {
                itemClickListener(kitapRes)
            }
        }
    }

}