package com.qubiz.farm.repos

import com.qubiz.farm.base.BaseRepo
import com.qubiz.farm.models.domain.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepo: BaseRepo<Product, Long> {

    @Query("""
        select p from Product p
        order by p.sold, p.created desc
    """)
    fun getProductHomePage(pageable: Pageable): Page<Product>

    @Query(
        """
           select p.* from product p
            inner join product_category pc 
            on p.id = pc.product_id
            where pc.category_id = :categoryId
            order by p.sold, p.created desc
        """,
        nativeQuery = true,
        countQuery = """
            select count(p.id) from product p
            inner join product_category pc 
            on p.id = pc.product_id
            where pc.category_id = :categoryId
            order by p.sold, p.created desc
        """
    )
    fun findAllByCategory(categoryId: Long, pageable: Pageable): Page<Product>
}
