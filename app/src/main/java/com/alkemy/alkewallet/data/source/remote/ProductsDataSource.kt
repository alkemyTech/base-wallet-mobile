package com.alkemy.alkewallet.data.source.remote

import android.util.Log
import com.alkemy.alkewallet.data.source.remote.service.AccountService
import com.alkemy.alkewallet.data.source.remote.service.TransactionService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductsDataSource @Inject constructor(
    private val transactionService: TransactionService,
    private val accountService: AccountService
) {
    fun transactions(url: String? = "/transactions") = flow {
        val page = url ?: "/transactions"
        emit(transactionService.getTransactions(page))
    }.catch {
        Log.e(this.javaClass.name, "Retrieve transactions failed", it)
    }.flowOn(Dispatchers.IO)

    fun accounts() = flow {
        emit(accountService.getAccounts())
    }.catch {
        Log.e(this.javaClass.name, "Retrieve accounts failed", it)
    }.flowOn(Dispatchers.IO)
}