package com.alkemy.alkewallet.data.model

import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName
import java.util.Date

data class TransactionsResponse(
    val previousPage: String,
    val nextPage: String,
    val data: List<Transaction>
)

data class Transaction(
    val id: Int,
    val amount: Double,
    val concept: String,
    val date: Date,
    val type: String,
    val accountId: Int,
    @SerializedName("to_account_id")
    val accountDestinationId: Int,
    val createdAt: Date,
    val updatedAt: Date
)

data class TransactionView(
    @DrawableRes val type: Int,
    val amount: String,
    val name: String,
    val date: String
)