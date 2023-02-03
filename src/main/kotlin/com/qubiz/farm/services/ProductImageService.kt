package com.qubiz.farm.services

import org.springframework.web.multipart.MultipartFile

interface ProductImageService {
    fun uploadImages(filenames: List<MultipartFile>, productId: Long): Map<String, Any>
    fun update(imgFilename: String, filename: MultipartFile): Map<String, Any>
    fun remove(id: Long)
}
