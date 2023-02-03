package com.qubiz.farm.repos

import com.qubiz.farm.base.BaseRepo
import com.qubiz.farm.models.domain.ShoppingCart
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ShoppingCartRepo: BaseRepo<ShoppingCart, Long> {

    @Query(
        """
            select count(i.id) from shopping_cart c
            inner join item i on i.cart_id = c.id
            where c.status = 0
            and c.user_id = :userId
        """,
         nativeQuery = true
    )
    fun findTotalItemAddedToCart(userId: Long): Long
    fun findAllByUserIdAndStatus(userId: Long, status: Int = 0, pageable: Pageable): Page<ShoppingCart>
    fun findByStoreIdAndUserIdAndStatus(storeId: Long, userId: Long, status: Int): ShoppingCart?
}
