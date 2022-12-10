package com.nextia.socioinfonavit.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nextia.socioinfonavit.R
import com.nextia.socioinfonavit.data.dto.Wallet
import com.nextia.socioinfonavit.databinding.ItemPurseBinding

class WalletsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listItems: MutableList<Wallet> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemPurseBinding>(layoutInflater, R.layout.item_purse,parent, false)
        return WalletViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is WalletViewHolder -> holder.bind(listItems[position])
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    private inner class WalletViewHolder(val binding: ItemPurseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(wallet: Wallet) {
            binding.tvNamePurse.text = wallet.name
            val adapter = BenevitsAdapter()
            binding.rvPurchase.adapter = adapter
            adapter.setData(wallet.benevit)
        }
    }

    fun setData(data: List<Wallet>) {
        listItems.clear()
        listItems.addAll(data)
        notifyDataSetChanged()
    }
}