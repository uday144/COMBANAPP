package com.example.combankapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Transaction(
    val amount: String,
    val id: String,
    val isPending: Boolean,
    val description: String,
    val category: String,
    val effectiveDate: String
): Parcelable