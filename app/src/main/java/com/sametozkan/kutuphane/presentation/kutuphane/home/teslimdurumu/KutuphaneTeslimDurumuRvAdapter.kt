package com.sametozkan.kutuphane.presentation.kutuphane.home.teslimdurumu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.databinding.ItemKutuphaneKitapIstekleriBinding
import com.sametozkan.kutuphane.databinding.ItemKutuphaneTeslimDurumuBinding

class KutuphaneTeslimDurumuRvAdapter :
    RecyclerView.Adapter<KutuphaneTeslimDurumuRvAdapter.ViewHolder> {

    var list: List<KitapKullaniciRes>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val teslimEdildiClickListener: (KitapKullaniciRes) -> Unit


    constructor(
        list: List<KitapKullaniciRes>, onaylaClickListener: (KitapKullaniciRes) -> Unit,
    ) {
        this.list = list
        this.teslimEdildiClickListener = onaylaClickListener
    }

    class ViewHolder(
        private val binding: ItemKutuphaneTeslimDurumuBinding,
        private val teslimEdildiClickListener: (KitapKullaniciRes) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(kitapKullaniciRes: KitapKullaniciRes) {
            binding.kitapKullaniciRes = kitapKullaniciRes
            binding.teslimEdildiButton.setOnClickListener {
                teslimEdildiClickListener(kitapKullaniciRes)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKutuphaneTeslimDurumuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, teslimEdildiClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(holder.adapterPosition))
    }
}