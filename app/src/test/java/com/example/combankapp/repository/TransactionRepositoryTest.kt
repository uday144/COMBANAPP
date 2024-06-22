package com.example.combankapp.repository

import com.example.combankapp.models.Account
import com.example.combankapp.models.Transaction
import com.example.combankapp.models.TransactionData
import com.example.combankapp.network.ApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class TransactionRepositoryTest{
    @Mock
    lateinit var apiService: ApiService

    // Dummy data
    private val dummyAccount = Account(
        bsb = "062005",
        accountNumber = "17095888",
        balance = "246.7",
        available = "226.76",
        accountName = "Complete Access"
    )

    private val dummyTransactions = listOf(
        Transaction(
            amount = "-14.19",
            id = "B829C8C5-4F1F-46F7-87A2-090C23DE7DA9",
            isPending = true,
            description = "Setapp (via Paddle.Net) +440808178853 GBR",
            category = "shopping",
            effectiveDate = "2021-02-28"
        ),
        Transaction(
            amount = "-42.69",
            id = "39062634-DFF7-4E23-B108-8289D07F9F73",
            isPending = true,
            description = "Linode Philadelphia PA",
            category = "business",
            effectiveDate = "2021-02-28"
        )
    )

    private val dummyTransactionData = TransactionData(
        account = dummyAccount,
        transactions = dummyTransactions,
        atms = emptyList()
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetTransaction_null() = runTest {
        Mockito.`when`(apiService.getTransactions()).thenReturn(null)

        val sut = TransactionRepository(apiService)
        val result = sut.getTransactions()
        assertNull(result )
    }

    @Test
    fun testGetTransactionList() = runTest {

        Mockito.`when`(apiService.getTransactions()).thenReturn(dummyTransactionData)

        val sut = TransactionRepository(apiService)
        val result = sut.getTransactions()
        assertNotNull(result)
    }
}