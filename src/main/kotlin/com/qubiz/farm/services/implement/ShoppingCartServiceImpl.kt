package com.qubiz.farm.services.implement

import com.qubiz.farm.dto.Response
import com.qubiz.farm.dto.response.CartResponse
import com.qubiz.farm.dto.response.ProductInCart
import com.qubiz.farm.exceptions.CustomException
import com.qubiz.farm.models.domain.Item
import com.qubiz.farm.models.domain.ShoppingCart
import com.qubiz.farm.models.enumeration.ShoppingCartStatus
import com.qubiz.farm.repos.ItemRepo
import com.qubiz.farm.repos.ProductRepo
import com.qubiz.farm.repos.ShoppingCartRepo
import com.qubiz.farm.services.ShoppingCartService
import com.qubiz.farm.utills.UserContext
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class ShoppingCartServiceImpl(
    private val shoppingCartRepo: ShoppingCartRepo,
    private val productRepo: ProductRepo,
    private val userContext: UserContext,
    private val response: Response
) : ShoppingCartService {

    @Transactional
    override fun addProductToCart(productId: Long, cartId: Long?, qty: Int?): Long {

        val product = productRepo.findById(productId).orElseThrow { CustomException(404, "Something went wrong, please retry.") }
        val customer = userContext.getCurrentUser() ?: throw CustomException(401, "Unauthorized")

        val cart = cartId?.let {
            shoppingCartRepo.findById(cartId).orElseThrow { CustomException(404, "You lose your cart?") }
        } ?: shoppingCartRepo.findByStoreIdAndUserIdAndStatus(product.store!!.id!!, customer.id!!, 0)
                ?: ShoppingCart(storeId = product.store?.id, user = customer)

        if (!cart.items.isNullOrEmpty()) {
            try {
                val item = cart.items!!.first { it.product!!.id == productId }
                if (qty!! <= -item.qyt!!) {
                    cart.items?.remove(item)
                    shoppingCartRepo.saveAndFlush(cart)
                    if (cart.items!!.size == 0) {
                        shoppingCartRepo.delete(cart)
                    }
                    return shoppingCartRepo.findTotalItemAddedToCart(customer.id!!)
                }
                else
                    cart.items!!.first { it.product!!.id == productId }.qyt = item.qyt!!.plus(qty)
            } catch (e: NoSuchElementException) {
                if (qty!! <= 0)
                    throw CustomException(400, "Meh, quantity should be positive number")
                cart.items!!.add(
                    Item(product, qty, cart)
                )
            }
        } else {
            if (cart.items != null && cart.items!!.isEmpty()) {
                cart.items?.add(Item(product, qty, cart))
            } else cart.items = mutableListOf(Item(product, qty, cart))
        }

        if (qty!! <= 0)
            throw CustomException(400, "Meh, quantity should be positive number")
        shoppingCartRepo.save(cart)
        return shoppingCartRepo.findTotalItemAddedToCart(customer.id!!)
    }

    @Transactional
    @Modifying
    override fun removeProductFromCart(productId: Long, cartId: Long): Long {

        val customer = userContext.getCurrentUser() ?: throw CustomException(401, "Unauthorized")
        val cart = shoppingCartRepo.findById(cartId).orElseThrow { CustomException(404, "You lose your cart?") }

        try {
            cart.items?.remove(cart.items!!.first { it.product!!.id == productId })
        } catch (e: NoSuchElementException) {
            throw CustomException(400, "Product not found")
        }
        if (cart.items!!.size == 0) {
            shoppingCartRepo.delete(cart)
            return shoppingCartRepo.findTotalItemAddedToCart(customer.id!!)
        }
        shoppingCartRepo.save(cart)
        return shoppingCartRepo.findTotalItemAddedToCart(customer.id!!)
    }

    override fun getCartList(page: Int, size: Int): Map<String, Any> {
        val customer = userContext.getCurrentUser() ?: throw CustomException(401, "Unauthorized")
        val res = shoppingCartRepo.findAllByUserIdAndStatus(customer.id!!, ShoppingCartStatus.PENDING.code, PageRequest.of(page, size)).map { cartToCartResponse(it) }
        return response.responseObject(res.content, res.totalElements)
    }


    fun cartToCartResponse(c: ShoppingCart): CartResponse {
        return CartResponse(
            c.id,
            c.storeId,
            c.items?.map {
                ProductInCart(
                    it.product?.id,
                    it.product?.name,
                    it.product?.price,
                    it.qyt,
                    it.product?.images?.map { i -> i.filename }
                )
            }
        )
    }

}
