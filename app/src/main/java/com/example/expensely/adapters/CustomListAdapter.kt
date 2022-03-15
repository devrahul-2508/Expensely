package com.example.expensely.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.expensely.databinding.ItemCustomDilogBinding
import com.example.expensely.view.fragments.AllTransactionsFragment

class CustomListAdapter(val fragment:Fragment, private val customList:List<String>): RecyclerView.Adapter<CustomListAdapter.ViewHolder>() {

    class ViewHolder(itemView:ItemCustomDilogBinding):RecyclerView.ViewHolder(itemView.root) {
            val title=itemView.tvText

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ItemCustomDilogBinding.inflate(LayoutInflater.from(fragment.requireContext()),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=customList[position]
        holder.title.text=item
        holder.itemView.setOnClickListener {
            if (fragment is AllTransactionsFragment){
                fragment.filterTransactions(item)
            }
        }

    }

    override fun getItemCount(): Int {
        return customList.size
    }
}