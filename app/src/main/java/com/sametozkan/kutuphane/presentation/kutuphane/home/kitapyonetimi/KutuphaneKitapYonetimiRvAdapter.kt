package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.databinding.ItemKitapBinding

class KutuphaneKitapYonetimiRvAdapter :
    RecyclerView.Adapter<KutuphaneKitapYonetimiRvAdapter.ViewHolder> {

    var list: List<KitapRes>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val isbnClickListener : (KitapRes) -> Unit

    constructor(list: List<KitapRes>, isbnClickListener: (KitapRes) -> Unit) {
        this.list = list
        this.isbnClickListener = isbnClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKitapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, isbnClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(holder.adapterPosition))
    }

    class ViewHolder(private val binding: ItemKitapBinding, private val isbnClickListener: (KitapRes) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(kitapRes: KitapRes) {
            binding.kitapRes = kitapRes
            binding.isbnTextView.setOnClickListener {
                isbnClickListener(kitapRes)
            }
            binding.linkColor = Color.parseColor("#B35100")
        }
    }

}