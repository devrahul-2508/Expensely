package com.example.expensely.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.expensely.R
import com.example.expensely.databinding.ItemTransactionLayoutBinding
import com.example.expensely.entities.Transaction
import com.example.expensely.utils.Constants
import com.example.expensely.view.fragments.AllTransactionsFragment
import com.example.expensely.view.fragments.DashBoardFragment

class TransactionAdapter(val fragment: Fragment) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactions: List<Transaction> = listOf()

    class TransactionViewHolder(itemView: ItemTransactionLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val transactionImage = itemView.transactionIconView
        val transactionName = itemView.transactionName
        val transactionCategory = itemView.transactionCategory
        var transactionAmount = itemView.transactionAmount
        val transactionDate=itemView.transactionDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionLayoutBinding.inflate(
            LayoutInflater.from(fragment.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = transactions[position]
        holder.transactionName.text = item.title
        holder.transactionCategory.text = item.tags
        holder.transactionDate.text=item.date

        Glide.with(fragment).load(R.drawable.ic_housing).into(holder.transactionImage)
        if (item.transactionType == "Income") {
            holder.transactionAmount.setTextColor(
                ContextCompat.getColor(
                    fragment.requireContext(),
                    R.color.income
                )
            )
            holder.transactionAmount.setText("+$" + item.amount.toString())
        } else {
            holder.transactionAmount.setTextColor(
                ContextCompat.getColor(
                    fragment.requireContext(),
                    R.color.expense
                )
            )
            holder.transactionAmount.setText("-$" + item.amount.toString())

        }
        when (item.tags) {
            "Housing" -> {
                Glide.with(fragment).load(R.drawable.ic_housing).into(holder.transactionImage)
            }
            "Transportation" -> {
                Glide.with(fragment).load(R.drawable.ic_transport).into(holder.transactionImage)
            }
            "Food" -> {
                Glide.with(fragment).load(R.drawable.ic_food).into(holder.transactionImage)
            }
            "Utilities" -> {
                Glide.with(fragment).load(R.drawable.ic_utlities).into(holder.transactionImage)
            }
            "Insurance" -> {
                Glide.with(fragment).load(R.drawable.ic_insurance).into(holder.transactionImage)
            }
            "Healthcare" -> {
                Glide.with(fragment).load(R.drawable.ic_medical).into(holder.transactionImage)
            }
            "Saving & Debts" -> {
                Glide.with(fragment).load(R.drawable.ic_savings).into(holder.transactionImage)
            }
            "Personal Spending" -> {
                Glide.with(fragment).load(R.drawable.ic_personal_spending).into(holder.transactionImage)
            }
            "Entertainment" -> {
                Glide.with(fragment).load(R.drawable.ic_entertainment).into(holder.transactionImage)
            }
            "Miscellaneous" -> {
                Glide.with(fragment).load(R.drawable.ic_others).into(holder.transactionImage)
            }

        }

        holder.itemView.setOnClickListener {
            if (fragment is DashBoardFragment){
                fragment.transactionDetails(item)
            }
            else if(fragment is AllTransactionsFragment){
                fragment.transactionDetails(item)
            }
        }


    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    fun transactionList(list: List<Transaction>) {
        transactions = list
        notifyDataSetChanged()
    }
}