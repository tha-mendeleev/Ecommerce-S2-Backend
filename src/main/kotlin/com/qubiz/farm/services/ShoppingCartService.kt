package com.qubiz.farm.services


interface ShoppingCartService {
    fun addProductToCart(productId: Long, cartId: Long?, qty: Int?): Long
    fun removeProductFromCart(productId: Long, cartId: Long): Long
    fun getCartList(page: Int, size: Int): Map<String, Any>
}
