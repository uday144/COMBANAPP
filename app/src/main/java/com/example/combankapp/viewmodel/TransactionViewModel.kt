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
    private val _transactionsData = MutableStateFlow<TransactionData?>(null)
    val transactionsData: StateFlow<TransactionData?> = _transactionsData

    init {
        viewModelScope.launch {
            repository.getTransactions().collect {
                _transactionsData.value = it
            }
        }
    }

}
