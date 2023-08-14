package com.alkemy.alkewallet.data.source.remote.service

import com.alkemy.alkewallet.data.model.TransactionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface TransactionService {
    @GET
    suspend fun getTransactions(@Url url: String): Response<TransactionsResponse>
}