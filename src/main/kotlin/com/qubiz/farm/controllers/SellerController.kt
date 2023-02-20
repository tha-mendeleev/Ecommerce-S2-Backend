package com.qubiz.farm.controllers

import com.qubiz.farm.AppConstants
import com.qubiz.farm.dto.Response
import com.qubiz.farm.dto.request.manageProductRequest
import com.qubiz.farm.models.domain.Category
import com.qubiz.farm.models.domain.Store
import com.qubiz.farm.services.CategoryService
import com.qubiz.farm.services.ProductService
import com.qubiz.farm.services.StoreService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstants.BASE_PATH + "/seller")
class SellerController(
    private val productService: ProductService,
    private val categoryService: CategoryService,
    private val storeService: StoreService,
    private val response: Response
) {

    @PostMapping("/store")
    fun createStore(@RequestBody req: Store): Any {
        return response.responseObject(storeService.create(req))
    }

    @GetMapping("/store")
    fun getStoreDetail(): Any {
        return response.responseObject(storeService.getStoreDetailByOwner())
    }

    @PostMapping("/category")
    fun createCategory(@RequestBody req: Category): Any {
        return response.responseObject(categoryService.create(req), 201, "Created")
    }

    @PutMapping("/category/{id}")
    fun updateCategory(@PathVariable id: Long, @RequestBody req: Category): Any {
        return response.responseObject(categoryService.update(id, req), 201, "Updated")
    }

    @GetMapping("/category/dropdown")
    fun getCategoryDropdown(): Any {
        return response.responseObject(categoryService.getDropdown())
    }

    @PostMapping("/product")
    fun createProduct(@RequestBody req: manageProductRequest): Any {
        return response.responseObject(productService.createProduct(req))
    }

    @PutMapping("/product/{id}")
    fun updateProduct(@RequestBody req: manageProductRequest, @PathVariable id: Long): Any {
        return response.responseObject((productService.update(id, req)), 201, "Updated")
    }

    @GetMapping("/product/list")
    fun getProductList(@RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "20") size:Int): Any {
        return productService.getProductListPageByStore(page, size)
    }
}
