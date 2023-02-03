package com.qubiz.farm.models.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.qubiz.farm.base.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "order_tb")
data class Order(
    var name: String? = null,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "order")
    var carts: MutableList<ShoppingCart>? = null,
    @JsonIgnore
    @ManyToOne
    val user: User? = null,
    @OneToMany(fetch = FetchType.LAZY)
    val orderDetails: MutableList<OrderDetail>? = null
): BaseEntity()
