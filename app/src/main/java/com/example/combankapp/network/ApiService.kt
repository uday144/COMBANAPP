package com.example.combankapp.network

import com.example.combankapp.models.TransactionData
import retrofit2.http.GET

interface ApiService {
    @GET("exercise.json")
    suspend fun getTransactions(): TransactionData
}