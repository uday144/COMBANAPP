package com.example.combankapp.di

import android.content.Context
import com.example.combankapp.network.ApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(MockInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl("http://localhost/")  // Dummy base URL
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


}

class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()

        val fileName = when {
            uri.endsWith("exercise.json") -> "exercise.json"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        val inputStream = context.assets.open(fileName)
        val json = inputStream.bufferedReader().use { it.readText() }

        return Response.Builder()
            .code(200)
            .message(json)
            .protocol(Protocol.HTTP_1_1)
            .request(chain.request())
            .body(json.toResponseBody("application/json".toMediaTypeOrNull()))
            .build()
    }
}

