package com.alkemy.alkewallet.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkemy.alkewallet.R
import com.alkemy.alkewallet.data.model.BalanceView
import com.alkemy.alkewallet.data.model.Result
import com.alkemy.alkewallet.data.model.TransactionView
import com.alkemy.alkewallet.data.model.User
import com.alkemy.alkewallet.data.source.remote.ProductsRepository
import com.alkemy.alkewallet.data.source.remote.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DateFormat
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _profileResult = MutableLiveData<Result<User>>()
    val profileResult: LiveData<Result<User>> = _profileResult

    private val _transactionsResult = MutableLiveData<Result<List<TransactionView>>>()
    val transactionsResult: LiveData<Result<List<TransactionView>>> = _transactionsResult

    private val _formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
    private var _lastPageTransactions: Boolean = false
    private var _nextPage: String? = null

    private val _balance = MutableLiveData<BalanceView>()
    val balance: LiveData<BalanceView> = _balance

    init {
        profile()
        loadTransactions()
        accounts()
    }

    private fun profile() = viewModelScope.launch {
        _profileResult.value = Result(loading = true)
        userRepository.profile().collect {
            if (it.isSuccessful) {
                val user = it.body()?.let { r ->
                    User(name = r.name, displayName = "${r.name} ${r.lastName}", email = r.email)
                } ?: User()
                _profileResult.value = Result(user)
            } else {
                _profileResult.value = Result(error = R.string.login_failed)
            }
        }
    }

    fun loadTransactions() = viewModelScope.launch {
        if (!_lastPageTransactions) {
            _transactionsResult.value = Result(loading = true)
            productsRepository.transactions(_nextPage).collect {
                if (it.isSuccessful) {
                    _lastPageTransactions = it.body()?.nextPage?.isNotEmpty() ?: true
                    _nextPage = it.body()?.nextPage

                    val transactions = it.body()?.data?.map { transaction ->
                        val isPayment = transaction.type == "payment"
                        TransactionView(
                            name = "Falta buscar el nombre",
                            amount = if (isPayment) "-$${transaction.amount}" else "+$${transaction.amount}",
                            date = _formatter.format(transaction.date),
                            type = if (isPayment) R.drawable.icon_send else R.drawable.icon_request
                        )
                    }
                    _transactionsResult.value = Result(transactions)
                } else {
                    _transactionsResult.value = Result(error = R.string.home_transactions_error)
                }
            }
        }
    }

    private fun accounts() = viewModelScope.launch {
        productsRepository.accounts().collect {
            if (it.isSuccessful) {
                val amount = it.body()?.sumOf { account -> account.money }
                _balance.value = BalanceView(String.format("$%.2f", amount))
            }
        }
    }

    fun isLastPage() = _lastPageTransactions
}