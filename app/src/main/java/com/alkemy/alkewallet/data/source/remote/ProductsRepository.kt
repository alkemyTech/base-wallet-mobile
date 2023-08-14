package com.alkemy.alkewallet.data.source.remote

import javax.inject.Inject

class ProductsRepository @Inject constructor(private val productsDataSource: ProductsDataSource) {
    fun transactions(url: String? = null) = productsDataSource.transactions(url)

    fun accounts() = productsDataSource.accounts()
}