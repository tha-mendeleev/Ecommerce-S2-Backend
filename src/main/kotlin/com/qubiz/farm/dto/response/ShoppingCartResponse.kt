package com.qubiz.farm.dto.response

import com.qubiz.farm.models.domain.Item


data class ShoppingCartResponse(
    val storeId: Long,
    val items: List<Item>
)
