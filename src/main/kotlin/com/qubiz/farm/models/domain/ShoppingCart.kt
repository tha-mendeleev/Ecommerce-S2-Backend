package com.qubiz.farm.models.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.qubiz.farm.base.BaseEntity
import com.qubiz.farm.models.enumeration.ShoppingCartStatus
import javax.persistence.*

@Entity
data class ShoppingCart(
    val storeId: Long? = null,
    @OneToMany(mappedBy = "cart", cascade = [CascadeType.ALL], orphanRemoval = true)
    var items: MutableList<Item>? = null,
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User? = null,
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    val order: Order? = null,
    @JsonIgnore
    var status: Int? = ShoppingCartStatus.PENDING.code
) : BaseEntity()
