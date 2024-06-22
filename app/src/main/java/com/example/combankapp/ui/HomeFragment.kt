package com.example.combankapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.combankapp.databinding.FragmentHomeBinding
import com.example.combankapp.models.Transaction
import com.example.combankapp.models.TransactionData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Collections
import java.util.TreeMap

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: TransactionViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.transactionsData.collect { data ->
                data?.let {
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
                Collections.reverseOrder()
            )
        for (transaction in transactions) {
            val date: String = transaction.effectiveDate
            if (!groupedTransactions.containsKey(date)) {
                groupedTransactions[date] = ArrayList()
            }
            groupedTransactions[date]?.add(transaction)
        }
        binding.recyclerView.setLayoutManager(LinearLayoutManager(requireContext()))
        binding.recyclerView.setAdapter(TransactionsAdapter(groupedTransactions){ transaction ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(transaction)
            findNavController().navigate(action)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}