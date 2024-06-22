package com.example.combankapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.combankapp.databinding.ItemDateGroupBinding
import com.example.combankapp.databinding.ItemTransactionBinding
import com.example.combankapp.models.Transaction


class TransactionsAdapter(groupedTransactions: Map<String, List<Transaction>?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<Any> = ArrayList()

    init {
        for ((key, value) in groupedTransactions) {
            items.add(key)
            items.addAll(value!!)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is String) VIEW_TYPE_DATE else VIEW_TYPE_TRANSACTION
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == VIEW_TYPE_DATE) {
            val binding: ItemDateGroupBinding =
                ItemDateGroupBinding.inflate(inflater, parent, false)
            return DateViewHolder(binding)
        } else {
            val binding: ItemTransactionBinding =
                ItemTransactionBinding.inflate(inflater, parent, false)
            return TransactionViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_DATE) {
            val date = items[position] as String
            (holder as DateViewHolder).bind(date)
        } else {
            val transaction = items[position] as Transaction
            (holder as TransactionViewHolder).bind(transaction)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    internal class DateViewHolder(private val binding: ItemDateGroupBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {

        fun bind(date: String?) {
            binding.tvDate.text = date
        }
    }

    internal class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {

        fun bind(transaction: Transaction) {
            binding.tvDescription.text = transaction.description
            binding.tvAmount.text = transaction.amount
        }
    }

    companion object {
        private const val VIEW_TYPE_DATE = 0
        private const val VIEW_TYPE_TRANSACTION = 1
    }
}
