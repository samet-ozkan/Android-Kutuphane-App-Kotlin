package com.sametozkan.kutuphane.presentation.kullanici.home.kitapistekleri.onaylandi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.data.dto.response.KutuphaneRes
import com.sametozkan.kutuphane.databinding.ItemKitapIstekleriOnaylandiBinding

class KitapIstekleriOnaylandiRvAdapter :
    RecyclerView.Adapter<KitapIstekleriOnaylandiRvAdapter.ViewHolder> {

    var list: List<KitapKullaniciRes>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val kutuphaneClickListener: (KutuphaneRes) -> Unit

    val isbnClickListener: (KitapRes) -> Unit

    constructor(
        list: List<KitapKullaniciRes>,
        kutuphaneClickListener: (KutuphaneRes) -> Unit,
        isbnClickListener: (KitapRes) -> Unit
    ) {
        this.list = list
        this.kutuphaneClickListener = kutuphaneClickListener
        this.isbnClickListener = isbnClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKitapIstekleriOnaylandiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, kutuphaneClickListener, isbnClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kitapKullaniciRes = list.get(holder.adapterPosition)
        holder.bindItem(kitapKullaniciRes)
    }

    class ViewHolder(
        private val binding: ItemKitapIstekleriOnaylandiBinding,
        private val kutuphaneClickListener: (KutuphaneRes) -> Unit,
        private val isbnClickListener: (KitapRes) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(kitapKullaniciRes: KitapKullaniciRes) {
            binding.kitapKullaniciRes = kitapKullaniciRes
            binding.kutuphaneAdiTv.setOnClickListener {
                kutuphaneClickListener(kitapKullaniciRes.kutuphane)
            }
            binding.kitapItem.isbnTextView.setOnClickListener {
                isbnClickListener(kitapKullaniciRes.kitap)
            }
        }
    }
}