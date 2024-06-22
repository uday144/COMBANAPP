package com.example.combankapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.combankapp.databinding.ActivityMainBinding
import com.example.combankapp.models.Transaction
import com.example.combankapp.models.TransactionData
import com.example.combankapp.ui.TransactionsAdapter
import com.example.combankapp.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Collections
import java.util.TreeMap


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: TransactionViewModel by viewModels()

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding?.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            viewModel.transactionsData.collect { data ->
                data?.let {
                    println("data$it")
                    processTransactionsData(it)
                }
            }
        }
    }

    private fun processTransactionsData(transactionData: TransactionData) {
        val transactions: List<Transaction> = transactionData.transactions

        // Group transactions by date, Using a TreeMap ensures that the keys (dates) are sorted in natural order, which can be useful if you need the transactions to be displayed in a sorted manner
        val groupedTransactions: MutableMap<String, MutableList<Transaction>> =
            TreeMap<String, MutableList<Transaction>>(
                Collections.reverseOrder<String>()
            )
        for (transaction in transactions) {
            val date: String = transaction.effectiveDate
            if (!groupedTransactions.containsKey(date)) {
                groupedTransactions[date] = ArrayList()
            }
            groupedTransactions[date]?.add(transaction)
        }
        binding?.recyclerView?.setLayoutManager(LinearLayoutManager(this));
        binding?.recyclerView?.setAdapter(TransactionsAdapter(groupedTransactions));
        println("groupedTransactions$groupedTransactions")
    }
}