package com.sametozkan.kutuphane.presentation.kutuphane.home.kitapistekleri

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.kutuphane.data.dto.response.KitapKullaniciRes
import com.sametozkan.kutuphane.data.dto.response.KitapRes
import com.sametozkan.kutuphane.data.dto.response.KullaniciRes
import com.sametozkan.kutuphane.databinding.ItemKutuphaneKitapIstekleriBinding

class KutuphaneKitapIstekleriRvAdapter :
    RecyclerView.Adapter<KutuphaneKitapIstekleriRvAdapter.ViewHolder> {

    var list: List<KitapKullaniciRes>
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val onaylaClickListener: (KitapKullaniciRes) -> Unit
    private val reddetClickListener: (KitapKullaniciRes) -> Unit
    private val isbnClickListener: (KitapRes) -> Unit
    private val kullaniciIdClickListener : (KullaniciRes) -> Unit


    constructor(
        list: List<KitapKullaniciRes>, onaylaClickListener: (KitapKullaniciRes) -> Unit,
        reddetClickListener: (KitapKullaniciRes) -> Unit, isbnClickListener: (KitapRes) -> Unit,
        kullaniciIdClickListener : (KullaniciRes) -> Unit
    ) {
        this.list = list
        this.onaylaClickListener = onaylaClickListener
        this.reddetClickListener = reddetClickListener
        this.isbnClickListener = isbnClickListener
        this.kullaniciIdClickListener = kullaniciIdClickListener
    }

    class ViewHolder(
        private val binding: ItemKutuphaneKitapIstekleriBinding,
        private val onaylaClickListener: (KitapKullaniciRes) -> Unit,
        private val reddetClickListener: (KitapKullaniciRes) -> Unit,
        private val isbnClickListener: (KitapRes) -> Unit,
        private val kullaniciIdClickListener : (KullaniciRes) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(kitapKullaniciRes: KitapKullaniciRes) {
            binding.kitapKullaniciRes = kitapKullaniciRes
            binding.onaylaButton.setOnClickListener {
                onaylaClickListener(kitapKullaniciRes)
            }
            binding.reddetButton.setOnClickListener {
                reddetClickListener(kitapKullaniciRes)
            }
            binding.kitapItem.isbnTextView.setOnClickListener {
                isbnClickListener(kitapKullaniciRes.kitap)
            }
            binding.kullaniciId.setOnClickListener {
                kullaniciIdClickListener(kitapKullaniciRes.kullanici)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKutuphaneKitapIstekleriBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onaylaClickListener, reddetClickListener, isbnClickListener, kullaniciIdClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list.get(holder.adapterPosition))
    }
}