package com.qubiz.farm.services

import com.qubiz.farm.base.BaseService
import com.qubiz.farm.dto.request.manageProductRequest
import com.qubiz.farm.dto.response.ViewProductDetailResponse
import com.qubiz.farm.models.domain.Product

interface ProductService: BaseService<Product, Long> {

    fun getProductById(id: Long): ViewProductDetailResponse
    /** For user view store's product*/
    fun getProductListByUser(storeId: Long, page: Int, size: Int): Map<String, Any>
    /** For seller view*/
    fun getProductListPageByStore(page: Int, size: Int): Map<String, Any>
    /** User view products home page*/
    fun getProductsHomePage(page: Int, size: Int): Map<String, Any>
    /** User view product detail */
    fun getProductDetail(id: Long): Map<String, Any>
    fun createProduct(req: manageProductRequest): Product
    fun searchProduct(startPrice: Double?, toPrice: Double?, query: String, page: Int, size: Int): Map<String, Any>
    fun searchProductByCategory(categoryId: Long, page: Int, size: Int): Map<String, Any>
    fun update(id: Long, req: manageProductRequest): Product

}
