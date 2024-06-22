package com.example.combankapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.combankapp.databinding.ItemDateGroupBinding
import com.example.combankapp.databinding.ItemTransactionBinding
import com.example.combankapp.models.Transaction


class TransactionsAdapter(
    private val groupedTransactions: Map<String, List<Transaction>?>,
    private val onItemClick: (Transaction) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        return if (viewType == VIEW_TYPE_DATE) {
            val binding = ItemDateGroupBinding.inflate(inflater, parent, false)
            DateViewHolder(binding)
        } else {
            val binding = ItemTransactionBinding.inflate(inflater, parent, false)
            TransactionViewHolder(binding)
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

    inner class DateViewHolder(private val binding: ItemDateGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(date: String) {
            binding.tvDate.text = date
        }
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {
            binding.tvDescription.text = transaction.description
            binding.tvAmount.text = transaction.amount.toString()
            itemView.setOnClickListener { onItemClick(transaction) }
        }
    }

    companion object {
        private const val VIEW_TYPE_DATE = 0
        private const val VIEW_TYPE_TRANSACTION = 1
    }
}

