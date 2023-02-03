package com.qubiz.farm.dto.response

data class CartResponse (
    val cartId: Long? = null,
    val storeId: Long? = null,
    val items: List<ProductInCart>? = null
    )

data class ProductInCart (
    val id: Long? = null,
    val name: String? = null,
    val price: Double? = 0.0,
    val qty: Int? = 0,
    val images: List<String?>? = null,
)



