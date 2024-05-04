package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapyonetimi.kitapekle.kitapolustur

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.data.dto.request.TurReq
import com.sametozkan.kutuphane.databinding.ItemTurEkleBinding

class TurlerRvAdapter : RecyclerView.Adapter<TurlerRvAdapter.ViewHolder> {

    var list: List<TurReq>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val itemClickListener: (TurReq) -> Unit

    constructor(list: List<TurReq>, itemClickListener: (TurReq) -> Unit) {
        this.list = list
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTurEkleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(holder.adapterPosition))
    }

    class ViewHolder(
        private val binding: ItemTurEkleBinding,
        private val itemClickListener: (TurReq) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(turReq: TurReq) {
            binding.turReq = turReq
            binding.remove.setOnClickListener {
                itemClickListener(turReq)
            }
        }
    }
}