package com.example.combankapp.ui

import com.example.combankapp.models.Account
import com.example.combankapp.models.Transaction
import com.example.combankapp.models.TransactionData
import com.example.combankapp.repository.TransactionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

// MockK imports
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.TestScope

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TransactionViewModelTest {

    private lateinit var viewModel: TransactionViewModel
    private val repository: TransactionRepository = mockk()

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
        // Set up repository to return dummy data
        coEvery { repository.getTransactions() } returns flowOf(dummyTransactionData)

        // Initialize ViewModel with the mocked repository
        viewModel = TransactionViewModel(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testTransactionsData() = runTest {
        val testDispatcher = UnconfinedTestDispatcher()
        val testScope = TestScope(testDispatcher)
        testScope.runTest {
            // Collect the transactionsData flow
            viewModel.transactionsData.collect { data ->
                // Assert that the emitted data is correct
                assertEquals(dummyTransactionData, data)
            }

            // Verify that the repository's getTransactions was called
            coVerify { repository.getTransactions() }
        }
    }
}