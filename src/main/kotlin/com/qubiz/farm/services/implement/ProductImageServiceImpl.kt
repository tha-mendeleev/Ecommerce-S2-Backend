package com.qubiz.farm.services.implement

import com.qubiz.farm.dto.Response
import com.qubiz.farm.exceptions.CustomException
import com.qubiz.farm.models.domain.ProductImage
import com.qubiz.farm.repos.ProductImageRepo
import com.qubiz.farm.repos.ProductRepo
import com.qubiz.farm.services.ProductImageService
import com.qubiz.farm.utills.UploadImage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class ProductImageServiceImpl(
    private val productRepo: ProductRepo,
    private val productImageRepo: ProductImageRepo,
    private val response: Response,
): ProductImageService {

    @Value("\${product-image-path}")
    private lateinit var productPath: String

    override fun uploadImages(filenames: List<MultipartFile>, productId: Long): Map<String, Any> {
        val pro = productRepo.findById(productId).orElseThrow { CustomException(404, "Product not found")}
        if ( pro.images == null) pro.images = mutableListOf()
        for (file in filenames) {
            pro.images?.add(
                ProductImage(
                    filename = UploadImage.storeImage(file, productPath),
                    product = pro
                )
            )
        }
        productRepo.save(pro)
        return response.responseObject(productRepo.save(pro))
    }

    @Transactional
    override fun update(imgFilename: String, filename: MultipartFile): Map<String, Any> {
        val img = productImageRepo.findByFilename(imgFilename) ?:  throw CustomException(400, "Image not found")
        img.filename = UploadImage.storeImage(filename, productPath)
        UploadImage.removeImage(imgFilename, productPath)
        return response.responseObject(productImageRepo.save(img))
    }

    override fun remove(filename: String) {
        val img = productImageRepo.findByFilename(filename) ?: throw CustomException(400, "Image not found")
        UploadImage.removeImage(img.filename!!, productPath)
        productImageRepo.delete(img)
    }

}
