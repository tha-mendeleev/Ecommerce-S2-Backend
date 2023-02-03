package com.qubiz.farm.repos

import com.qubiz.farm.base.BaseRepo
import com.qubiz.farm.models.domain.ProductImage
import org.springframework.stereotype.Repository

@Repository
interface ProductImageRepo: BaseRepo<ProductImage, Long> {
    fun findByFilename(filename: String): ProductImage?
}
