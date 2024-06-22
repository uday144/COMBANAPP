package com.example.combankapp

import com.example.combankapp.network.ApiService
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TransactionAPITest {

    lateinit var mockWebServer: MockWebServer
    lateinit var apiService: ApiService

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    @Test
    fun testEmptyTransaction() = runTest{
        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getTransactions()
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.transactions.isEmpty())
    }

    @Test
    fun testGetTransaction() = runTest{
        val mockResponse = MockResponse()
        val content = Helper.readFileResource("/response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getTransactions()
        mockWebServer.takeRequest()

        Assert.assertTrue( response.transactions.isNotEmpty())
    }


    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}












