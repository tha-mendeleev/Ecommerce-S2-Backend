package com.qubiz.farm.repos

import com.qubiz.farm.base.BaseRepo
import com.qubiz.farm.models.domain.Product
import com.qubiz.farm.models.domain.Store
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query

interface StoreRepo: BaseRepo<Store, Long> {
    fun getStoreByOwnerId(owner_id: Long): Store?

    @Query(
        """
            SELECT p FROM Product p
            WHERE p.store.id = :storeId
            ORDER BY p.created
        """
    )
    fun getProductByStoreListPage(storeId: Long, pageable: Pageable): Page<Product>

    @Query(
        """
            SELECT p FROM Product p
            WHERE p.store.id = :storeId
            ORDER BY p.sold, p.lastModified DESC
        """
    )
    fun getProductFromStore(storeId: Long, pageable: Pageable): Page<Product>

    @Query("""
        SELECT COUNT(s) > 0 FROM Store s
        WHERE s.owner.id = :owner_id
    """)
    fun existsByOwnerId(owner_id: Long): Boolean

    fun existsByName(name: String): Boolean
}
