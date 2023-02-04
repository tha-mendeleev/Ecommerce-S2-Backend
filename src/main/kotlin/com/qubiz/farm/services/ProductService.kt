package com.qubiz.farm.services

import com.qubiz.farm.dto.request.manageProductRequest
import com.qubiz.farm.dto.response.ViewProductDetailResponse
import com.qubiz.farm.dto.response.ViewProductResponse

interface ProductService {

    fun getProductById(id: Long): ViewProductDetailResponse
    /** For user view store's product*/
    fun getStoreProductListByCustomer(storeId: Long, page: Int, size: Int): Map<String, Any>
    /** For seller view*/
    fun getProductListPageByStore(page: Int, size: Int): Map<String, Any>
    /** User view products home page*/
    fun getProductsHomePage(page: Int, size: Int): Map<String, Any>
    /** User view product detail */
    fun getProductDetail(id: Long): Map<String, Any>
    fun createProduct(req: manageProductRequest): ViewProductResponse
    fun searchProduct(startPrice: Double?, toPrice: Double?, query: String, page: Int, size: Int): Map<String, Any>
    fun searchProductByCategory(categoryId: Long, page: Int, size: Int): Map<String, Any>
    fun update(id: Long, req: manageProductRequest): ViewProductResponse
    fun remove(req: Long)

}
