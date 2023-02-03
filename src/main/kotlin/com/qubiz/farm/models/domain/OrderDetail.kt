package com.qubiz.farm.models.domain

import com.qubiz.farm.base.BaseEntity
import javax.persistence.*

@Entity
data class OrderDetail(
    var name: String? = null,
    @ManyToOne
    @JoinColumn(name = "storeId", foreignKey = ForeignKey(name = "fk_store_id"), referencedColumnName = "id")
    val store: Store? = null,
    @ManyToOne
    @JoinColumn(name = "orderId", foreignKey = ForeignKey(name = "fk_order_id"), referencedColumnName = "id")
    val order: Order? = null,
    @OneToMany(cascade = [CascadeType.ALL])
    val products: MutableList<Product>? = null
): BaseEntity()
