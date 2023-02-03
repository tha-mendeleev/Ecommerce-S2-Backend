package com.qubiz.farm.repos

import com.qubiz.farm.base.BaseRepo
import com.qubiz.farm.models.domain.Item
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping

@Repository
interface ItemRepo: BaseRepo<Item, Long> {

    @Modifying
    fun deleteByCartIdAndProductId(cart_id: Long, product_id: Long)
}
