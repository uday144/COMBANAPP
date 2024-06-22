package com.example.combankapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.combankapp.R
import com.example.combankapp.databinding.ItemDateGroupBinding
import com.example.combankapp.databinding.ItemTransactionBinding
import com.example.combankapp.models.Transaction
import com.example.combankapp.util.Utils
import kotlin.math.abs


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
            binding.tvDate.text = Utils.parseStringToDate(date)
        }
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {
            binding.tvDescription.text = if(transaction.isPending) itemView.context.getString(R.string.pending_trn, itemView.context.getString(R.string.pending), transaction.description)
            else transaction.description
            binding.tvAmount.text =  itemView.context.getString(R.string.available_balance_neg, transaction.amount.substring(1))
            binding.icon.setImageDrawable(ContextCompat.getDrawable(itemView.context, Utils.getCategoryImage(transaction.category)))
            itemView.setOnClickListener { onItemClick(transaction) }
        }
    }

    companion object {
        private const val VIEW_TYPE_DATE = 0
        private const val VIEW_TYPE_TRANSACTION = 1
    }
}

