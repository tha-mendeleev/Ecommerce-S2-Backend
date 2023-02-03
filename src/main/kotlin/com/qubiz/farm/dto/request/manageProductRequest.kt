package com.qubiz.farm.dto.request

import com.qubiz.farm.models.domain.Category

data class manageProductRequest (
    val name: String? = null,
    val qty: Int? = 0,
    val sold: Int? = 0,
    val price: Double? = 0.0,
    val description: String? = null,
    val categories: List<Category>
    )
