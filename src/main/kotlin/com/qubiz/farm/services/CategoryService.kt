package com.qubiz.farm.services

import com.qubiz.farm.base.BaseService
import com.qubiz.farm.models.domain.Category
import org.springframework.web.multipart.MultipartFile

interface CategoryService: BaseService<Category, Long>{
    fun getDropdown(): List<Category>
    fun getDropdown(page: Int, size: Int): Map<String, Any>
    fun getList(): List<Category>
    fun uploadImages(filename: MultipartFile, categoryId: Long): Map<String, Any>
    fun updateImages(filename: MultipartFile, categoryId: Long): Map<String, Any>
    fun update(id: Long, req: Category): Category
}
