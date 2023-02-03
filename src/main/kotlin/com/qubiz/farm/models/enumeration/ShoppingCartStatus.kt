package com.qubiz.farm.models.enumeration

enum class ShoppingCartStatus(val code: Int) {

    CHECKOUT(1),
    PENDING(0);

    companion object {
        fun fromCode(code: Int): ShoppingCartStatus = ShoppingCartStatus.values().first { it.code == code }
        fun fromCode(name: String): ShoppingCartStatus = ShoppingCartStatus.values().first { it.name == name }
    }
}
