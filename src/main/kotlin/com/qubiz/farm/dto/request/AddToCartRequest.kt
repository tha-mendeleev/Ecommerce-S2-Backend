package com.qubiz.farm.dto.request

data class AddToCartRequest(
    val productId: Long, val cartId: Long?, val qty: Int? = 1
)
