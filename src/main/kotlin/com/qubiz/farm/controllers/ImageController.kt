package com.qubiz.farm.controllers

import com.qubiz.farm.AppConstants
import com.qubiz.farm.dto.Response
import com.qubiz.farm.services.CategoryService
import com.qubiz.farm.services.ProductImageService
import com.qubiz.farm.services.StoreService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(AppConstants.BASE_PATH + "/seller")
class ImageController(
    private val productImageService: ProductImageService,
    private val categoryService: CategoryService,
    private val storeService: StoreService,
    private val response: Response
) {

    @PostMapping("/product/upload-image/{product-id}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadProductImage(@PathVariable("product-id") productId: Long, @RequestParam files: List<MultipartFile>): Any {
        return productImageService.uploadImages(files, productId)
    }

    @PostMapping("/product/update-image/{filename:.+}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateProductImage(@PathVariable filename: String, @RequestParam file: MultipartFile): Any {
        return productImageService.update(filename, file)
    }

    @PostMapping("/product/remove-image/{}")
    fun removeProductImage(@PathVariable imgId: Long): Any {
        productImageService.remove(imgId)
        return response.responseCodeWithMessage(200, "Success")
    }

    @PostMapping("/category/image/upload/{category-id}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadCategoryImage(@PathVariable("category-id") categoryId: Long, @RequestParam file: MultipartFile): Any {
        return categoryService.uploadImages(file, categoryId)
    }

    @PutMapping("/category/image/update/{id}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateCategoryImage(@PathVariable id: Long, @RequestParam file: MultipartFile): Any {
        return response.responseObject(categoryService.updateImages(file, id))
    }

    @PostMapping("/store/upload-profile-image",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadStoreProfileImage(@RequestParam file: MultipartFile): Any {
        return storeService.uploadProfileImage(file)
    }

    @PostMapping("/store/upload-cover-image",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadStoreCoverImage(@RequestParam file: MultipartFile): Any {
        return storeService.uploadCoverImage(file)
    }
}
