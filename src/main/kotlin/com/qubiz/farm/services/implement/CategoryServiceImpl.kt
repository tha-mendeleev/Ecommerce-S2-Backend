package com.qubiz.farm.services.implement

import com.qubiz.farm.dto.Response
import com.qubiz.farm.exceptions.CustomException
import com.qubiz.farm.models.domain.Category
import com.qubiz.farm.repos.CategoryRepo
import com.qubiz.farm.services.CategoryService
import com.qubiz.farm.utills.UploadImage
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.persistence.criteria.Predicate
import kotlin.collections.ArrayList

@Service
class CategoryServiceImpl(
    private val categoryRepo: CategoryRepo,
    private val response: Response
) : CategoryService {

    @Value("\${category-image-path}")
    private lateinit var cateImageDir: String

    override fun getDropdown(): List<Category> {
        return categoryRepo.findAll()
    }

    override fun getDropdown(page: Int, size: Int): Map<String, Any> {
        val res = categoryRepo.findAll(PageRequest.of(page, size))
        return response.responseObject(res.content, res.totalElements)
    }

    override fun queryList(query: String, page: Int, size: Int): Map<String, Any> {
        val cats = categoryRepo.findAll(
            { root, _, cb ->
                cb.like(cb.lower(root.get("name")), "%${query.lowercase(Locale.getDefault())}%")
            }, PageRequest.of(page, size)
        )
        return response.responseObject(cats.content, cats.totalElements)
    }

    override fun uploadImages(filename: MultipartFile, categoryId: Long): Map<String, Any> {
        val cat = categoryRepo.findById(categoryId).orElseThrow { CustomException(404, "Product not found") }
        cat.imageFileName = UploadImage.storeImage(filename, cateImageDir)
        return response.responseObject(categoryRepo.save(cat))
    }

    override fun updateImages(filename: MultipartFile, categoryId: Long): Map<String, Any> {
        val cat = categoryRepo.findById(categoryId).orElseThrow { CustomException(404, "Product not found") }
        if (cat.imageFileName == null) throw CustomException(400, "Image not found")
        UploadImage.removeImage(cat.imageFileName!!, cateImageDir)
        cat.imageFileName = UploadImage.storeImage(filename, cateImageDir)
        return response.responseObject(categoryRepo.save(cat))
    }

    override fun update(id: Long, req: Category): Category {
        val cat = categoryRepo.findById(id).orElseThrow { CustomException(404, "Category not found") }
        cat.name = req.name
        return categoryRepo.save(cat)
    }

    override fun create(req: Category): Category {
        return categoryRepo.save(req)
    }

    @Transactional
    override fun update(req: Category): Category {
        return Category()
    }

    override fun delete(id: Long): Map<String, Any> {
        val cat = categoryRepo.findById(id).orElseThrow { CustomException(404, "Category not found") }
        if (cat.imageFileName != null)
            UploadImage.removeImage(cat.imageFileName!!, cateImageDir)
        categoryRepo.delete(cat)
        return response.responseCodeWithMessage(200, "Success")
    }

    @Transactional
    override fun remove(req: Long) {

    }
}
