package com.qubiz.farm.controllers

import com.qubiz.farm.AppConstants
import com.qubiz.farm.dto.Response
import com.qubiz.farm.dto.request.AddToCartRequest
import com.qubiz.farm.dto.request.RemoveFromCartReq
import com.qubiz.farm.services.CategoryService
import com.qubiz.farm.services.ProductService
import com.qubiz.farm.services.ShoppingCartService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(AppConstants.BASE_PATH + "/customer")
class CustomerController(
    private val shoppingCartService: ShoppingCartService,
    private val productService: ProductService,
    private val categoryService: CategoryService,
    private val response: Response
) {

    @PostMapping("/cart/add-to-cart")
    fun addProductToCart(@RequestBody req: AddToCartRequest): Any {
        return response.responseObject(shoppingCartService.addProductToCart(req.productId, req.cartId, req.qty))
    }

    @PostMapping("/cart/remove-from-cart")
    fun removeProductFromCart(@RequestBody req: RemoveFromCartReq): Any {
        return response.responseObject(shoppingCartService.removeProductFromCart(req.productId, req.cartId))
    }

    @GetMapping("/cart/list")
    fun getCartList(@RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "10") size: Int): Any {
        return shoppingCartService.getCartList(page, size)
    }

    @GetMapping("/view-product")
    fun viewProductHomePage(@RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "20") size: Int): Any {
        return productService.getProductsHomePage(page, size)
    }

    @GetMapping("/view-product/{id}")
    fun viewProductDetail(@PathVariable id: Long): Any {
        return productService.getProductDetail(id)
    }

    @GetMapping("/category/dropdown")
    fun getCategoryDropdown(@RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "6") size: Int): Any {
        return categoryService.getDropdown(page, size)
    }

    @GetMapping("/view-product/search")
    fun searchProduct(@RequestParam(name = "start-price") startPrice: Double?, @RequestParam(name = "to-price") toPrice: Double?, @RequestParam query: String, @RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "10") size: Int ): Any {
        return productService.searchProduct(startPrice, toPrice, query, page, size)
    }

    @GetMapping("/view-product/by-category/{id}")
    fun searchProductByCategory(@PathVariable id: Long, @RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "20") size: Int): Any {
        return productService.searchProductByCategory(id, page, size)
    }

    @GetMapping("/view-product/store/{storeId}")
    fun viewStoreProductByCustomer(@PathVariable storeId: Long, @RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "20") size: Int): Any {
        return productService.getStoreProductListByCustomer(storeId, page, size)
    }
}
