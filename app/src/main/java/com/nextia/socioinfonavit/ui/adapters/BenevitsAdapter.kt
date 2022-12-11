package com.nextia.socioinfonavit.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nextia.socioinfonavit.R
import com.nextia.socioinfonavit.core.helpers.differenceDay
import com.nextia.socioinfonavit.data.dto.Benevit
import com.nextia.socioinfonavit.databinding.ItemBenevitLockedBinding
import com.nextia.socioinfonavit.databinding.ItemBenevitUnlockedBinding
import java.lang.Exception

const val TYPE_LOCKED = 0
const val TYPE_UNLOCKED = 1
class BenevitsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listItems: MutableList<Benevit> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            TYPE_LOCKED -> LockedBenevitViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_benevit_locked,parent, false))
            TYPE_UNLOCKED -> UnLockedBenevitViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_benevit_unlocked,parent, false))
            else -> LockedBenevitViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_benevit_locked,parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is UnLockedBenevitViewHolder -> holder.bind(listItems[position])
            is LockedBenevitViewHolder -> holder.bind(listItems[position])
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }


    override fun getItemViewType(position: Int): Int {
        return listItems[position].typeLocket
    }

    private inner class UnLockedBenevitViewHolder(val binding: ItemBenevitUnlockedBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(benevit: Benevit) {
            binding.clHeader.setBackgroundColor(parseColor(binding.root.context, benevit.primaryColor))
            benevit.territory = benevit.territories.lastOrNull()?.name?:""
            Glide.with(binding.ivHeader.context).load(benevit.ally.mini_logo_full_path).into(binding.ivHeader)
            val days = differenceDay(benevit.expirationDate)
            val expire = binding.root.context.resources.getQuantityString(R.plurals.plural_expire_date, days.toInt(), days)
            benevit.expiration = expire
            binding.benevit = benevit

        }
    }

    private inner class LockedBenevitViewHolder(val binding: ItemBenevitLockedBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(benevit: Benevit) {
            benevit.territory = benevit.territories.lastOrNull()?.name?:""
            Glide.with(binding.ivHeaderBenefitUnLocked.context).load(benevit.vectorFullPath).into(binding.ivHeaderBenefitUnLocked)
            binding.benevit = benevit

        }
    }

    fun parseColor(context: Context, color: String): Int{
        return try {
            Color.parseColor(color)
        } catch (e: Exception){
            context.getColor(R.color.red)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(benevits: List<Benevit>) {
        listItems.clear()
        listItems.addAll(benevits)
        notifyDataSetChanged()
    }
}