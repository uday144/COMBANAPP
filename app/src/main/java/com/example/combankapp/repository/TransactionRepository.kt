package com.example.combankapp.repository

import com.example.combankapp.models.TransactionData
import com.example.combankapp.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(private val apiService: ApiService) {
    fun getTransactions(): Flow<TransactionData> = flow {
        emit(apiService.getTransactions())
    }.flowOn(Dispatchers.IO)
}