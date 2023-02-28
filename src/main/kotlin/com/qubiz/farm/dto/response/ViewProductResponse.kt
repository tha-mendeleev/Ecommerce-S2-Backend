package com.qubiz.farm.dto.response

import com.qubiz.farm.models.domain.Category

data class ViewProductResponse (
    val id: Long? = null,
    val name: String? = null,
    val qty: Int? = 0,
    val sold: Int? = 0,
    val price: Double? = 0.0,
    val description: String? = null,
    val images: List<String>? = null,
)

data class ViewProductDetailSellerResponse (
    val id: Long? = null,
    val name: String? = null,
    val qty: Int? = 0,
    val sold: Int? = 0,
    val price: Double? = 0.0,
    val description: String? = null,
    val categories: List<Category>? = null,
    val images: List<String>? = null
)

data class ViewProductDetailResponse (
    val id: Long? = null,
    val name: String? = null,
    val qty: Int? = 0,
    val sold: Int? = 0,
    val price: Double? = 0.0,
    val description: String? = null,
    val store: StoreInProductResponse? = null,
    val images: List<String>? = null
)

data class StoreInProductResponse (
    val id: Long? = null,
    val name: String? = null,
    val profileImage: String? = null
)
