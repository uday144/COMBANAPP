package com.example.combankapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.combankapp.models.TransactionData
import com.example.combankapp.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val repository: TransactionRepository) : ViewModel() {
    private val _transactions = MutableStateFlow<TransactionData?>(null)
    val transactions: StateFlow<TransactionData?> = _transactions

    init {
        viewModelScope.launch {
            repository.getTransactions().collect {
                _transactions.value = it
            }
        }
    }
}
