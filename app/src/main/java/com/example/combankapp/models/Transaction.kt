package com.example.combankapp.models

data class Transaction(
    val amount: String,
    val id: String,
    val isPending: Boolean,
    val description: String,
    val category: String,
    val effectiveDate: String
)