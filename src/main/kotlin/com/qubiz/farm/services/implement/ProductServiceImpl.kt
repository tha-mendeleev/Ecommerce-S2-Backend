package com.qubiz.farm.services.implement

import com.qubiz.farm.dto.Response
import com.qubiz.farm.dto.request.manageProductRequest
import com.qubiz.farm.dto.response.StoreInProductResponse
import com.qubiz.farm.dto.response.ViewProductDetailResponse
import com.qubiz.farm.dto.response.ViewProductDetailSellerResponse
import com.qubiz.farm.dto.response.ViewProductResponse
import com.qubiz.farm.exceptions.CustomException
import com.qubiz.farm.models.domain.Product
import com.qubiz.farm.repos.ProductRepo
import com.qubiz.farm.repos.StoreRepo
import com.qubiz.farm.services.ProductImageService
import com.qubiz.farm.services.ProductService
import com.qubiz.farm.utills.UserContext
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.criteria.Predicate
import kotlin.collections.ArrayList

@Service
class ProductServiceImpl(
    private val userContext: UserContext,
    private val storeRepo: StoreRepo,
    private val productRepo: ProductRepo,
    private val response: Response,
    private val productImageService: ProductImageService
    ): ProductService {

    override fun getProductDetailBySeller(id: Long): Map<String, Any> {
        val res = productRepo.findById(id).orElseThrow{CustomException(404, "Product not found")}
        return response.responseObject(res.pToDetailSellerResponse())
    }

    override fun getProductById(id: Long): ViewProductDetailResponse {
        return productRepo.findById(id).orElseThrow { CustomException(404, "Product not found") }.pToDetailResponse()
    }

    override fun getStoreProductListByCustomer(storeId: Long, page: Int, size: Int): Map<String, Any> {
        val res = storeRepo.getProductFromStore(storeId, PageRequest.of(page, size)).map{ it.pToResponse() }
        return response.responseObject(res.content, res.totalElements)
    }

    override fun getProductListPageByStore(page: Int, size: Int): Map<String, Any> {
        val currUser = userContext.getCurrentUser() ?: throw CustomException(403, "Something went wrong please try again.")
        val store = storeRepo.getStoreByOwnerId(currUser.id!!) ?: throw CustomException(400, "User has no store !!")
        val res = storeRepo.getProductByStoreListPage(store.id!!, PageRequest.of(page, size)).map { it.pToResponse() }
        return response.responseObject(res.content, res.totalElements)
    }

    override fun getProductsHomePage(page: Int, size: Int): Map<String, Any> {
        val res = productRepo.getProductHomePage(PageRequest.of(page, size)).map {it.pToResponse()}
        return response.responseObject(res.content, res.totalElements)
    }

    override fun getProductDetail(id: Long): Map<String, Any> {
        val pro = productRepo.findById(id).orElseThrow { CustomException(404, "Product not found") }.pToDetailResponse()
        return response.responseObject(pro)
    }

    override fun createProduct(req: manageProductRequest): ViewProductResponse {
        val createdBy = userContext.getCurrentUser() ?: throw CustomException(403, "Something went wrong please try again.")
        val store = storeRepo.getStoreByOwnerId(createdBy.id!!) ?: throw CustomException(400, "Storeless seller oops!!")
        val product = Product(
            name = req.name,
            qty = req.qty,
            sold = req.sold,
            price = req.price,
            description = req.description,
            categories = req.categories as MutableList,
            store = store
        )
        return productRepo.save(product).pToResponse()
    }

    override fun searchProduct(startPrice: Double?, toPrice: Double?, query: String, page: Int, size: Int): Map<String, Any> {
        val res = productRepo.findAll({ root, cq, cb ->
            val predicate = ArrayList<Predicate>()
            predicate.add(
                cb.like(cb.lower(root.get("name")), "%${query.lowercase(locale = Locale.getDefault())}%")
            )
            if (startPrice != null && toPrice != null)
                predicate.add(cb.between(root.get("price"), startPrice, toPrice))
            else if (startPrice != null) {
                predicate.add(cb.greaterThanOrEqualTo(root.get("price"), startPrice))
            } else if (toPrice != null) {
                predicate.add(cb.lessThanOrEqualTo(root.get("price"), toPrice))
            }
            cq.orderBy(cb.desc(root.get<Int>("sold")))
            cb.and(*predicate.toTypedArray())
        }, PageRequest.of(page, size)).map { it.pToResponse() }
        return response.responseObject(res.content, res.totalElements)
    }

    override fun searchProductByCategory(categoryId: Long, page: Int, size: Int): Map<String, Any> {
        val res = productRepo.findAllByCategory(categoryId, PageRequest.of(page, size)).map { it.pToResponse() }
        return response.responseObject(res.content, res.totalElements)
    }

    override fun update(id: Long, req: manageProductRequest): ViewProductResponse {
        val pro = productRepo.findById(id).orElseThrow { CustomException(400, "Product not found")}
        pro.name = req.name
        pro.qty = req.qty
        pro.sold = req.sold
        pro.price = req.price
        pro.description = req.description
        pro.categories = req.categories as MutableList
        return productRepo.save(pro).pToResponse()
    }

    override fun remove(req: Long): Map<String, Any> {
        val pro = productRepo.findById(req).orElseThrow { CustomException(400, "Product not found")}
        pro.images?.forEach { productImageService.remove(it.filename!!) }
        productRepo.delete(pro)
        return response.responseCodeWithMessage(200, "Success")
    }

    private fun Product.pToResponse() = ViewProductResponse(
        this.id,
        this.name,
        this.qty,
        this.sold,
        this.price,
        this.description,
        this.images?.map { it.filename!! }
    )

    private fun Product.pToDetailResponse() = ViewProductDetailResponse(
        this.id,
        this.name,
        this.qty,
        this.sold,
        this.price,
        this.description,
        StoreInProductResponse(
            this.store?.id,
            this.store?.name,
            this.store?.profileImage
        ),
        this.images?.map { it.filename!! }
    )

    private fun Product.pToDetailSellerResponse() = ViewProductDetailSellerResponse(
        this.id,
        this.name,
        this.qty,
        this.sold,
        this.price,
        this.description,
        this.categories,
        this.images?.map { it.filename!! }
    )
}
