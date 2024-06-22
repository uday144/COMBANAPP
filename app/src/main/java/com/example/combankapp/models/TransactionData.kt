package com.example.combankapp.models

data class TransactionData(
    val account: Account,
    val transactions: List<Transaction>,
    val atms: List<ATM>
)